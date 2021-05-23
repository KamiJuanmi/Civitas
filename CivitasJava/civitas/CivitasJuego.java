package civitas;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import GUI.Dado;

public class CivitasJuego {

    private int indiceJugadorActual;
    private Tablero tablero;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private ArrayList<Jugador> jugadores;
    private MazoSorpresas mazo;

    private void avanzaJugador() {

        Jugador jugadorActual = getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = this.tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(this.indiceJugadorActual, this.jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    }

    public boolean cancelarHipoteca(int ip) 
    {
        return this.getJugadorActual().cancelarHipoteca(ip);
    }

    public CivitasJuego(ArrayList<String> nombres) {

        this.jugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            Jugador jug = new Jugador(nombres.get(i));
            this.jugadores.add(jug);
        }

        this.gestorEstados = new GestorEstados();
        this.estado = this.gestorEstados.estadoInicial();
        this.indiceJugadorActual = Dado.getInstance().quienEmpieza(nombres.size());
        this.mazo = new MazoSorpresas();
        this.inicializarTablero(this.mazo);
        this.inicializarMazoSorpresas(this.tablero);

    }

    public boolean comprar() {

        Jugador jugadorActual = this.jugadores.get(this.indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = this.tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = ((Casilla_Calle)casilla).getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        return res;

    }

    public boolean construirCasa(int ip) 
    {
        return this.getJugadorActual().construirCasa(ip);
    }

    public boolean construirHotel(int ip) 
    {
        return this.getJugadorActual().construirHotel(ip);
    }

    private void contabilizarPasosPorSalida(Jugador jugadorActual) 
    {
        while ( tablero.getPorSalida() > 0) {
            
            jugadorActual.pasaPorSalida();
        }
    }

    public boolean finalDelJuego() {

        boolean fin = false;

        for (int i = 0; i < jugadores.size() && !fin; i++) {
            fin = jugadores.get(i).enBancarrota();
        }
        
        return fin;
    }

    public Casilla getCasillaActual() {

        Casilla ret=this.tablero.getCasillas().get((this.getJugadorActual().getNumCasillaActual()));
        return ret;

    }

    public Jugador getJugadorActual() {

        return this.jugadores.get(this.indiceJugadorActual);

    }

    public boolean hipotecar(int ip) {

        return this.getJugadorActual().hipotecar(ip);

    }

    public String infoJugadorTexto() {

        return this.jugadores.get(this.indiceJugadorActual).toString();

    }

    private void inicializarMazoSorpresas(Tablero tablero) {

        Random rand = new Random();
        this.mazo.alMazo(new Sorpresa_JugadorEspeculador(500,"EL JUGADOR VA A CAMBIAR A ESPECULATORE"));
        this.mazo.alMazo(new Sorpresa_PagarCobrar(1500,
                "El jugador al que se le aplica esta sorpresa recibe la cantidad indicada."));
        this.mazo.alMazo(new Sorpresa_PagarCobrar(-2000,
                "El jugador al que se le aplica esta sorpresa recibe la cantidad indicada."));

        int numCas_rand1 = rand.nextInt(10) + 1;
        int numCas_rand2 = rand.nextInt(10) + 1;

        while (numCas_rand1 == this.tablero.getCarcel()) {
            numCas_rand1 = rand.nextInt(10) + 1;
        }

        while (numCas_rand2 == this.tablero.getCarcel()) {
            numCas_rand2 = rand.nextInt(10) + 1;
        }

        this.mazo.alMazo(new Sorpresa_IrCasilla(this.tablero, numCas_rand1, "Vaya a la casilla indicada."));
        this.mazo.alMazo(new Sorpresa_IrCasilla(this.tablero,numCas_rand2, "Vaya a la casilla indicada."));
        this.mazo.alMazo(new Sorpresa_IrCasilla(this.tablero, this.tablero.getCarcel(), "Vaya a la casilla indicada."));

        this.mazo.alMazo(new Sorpresa_PorCasaHotel( 250,
                "El jugador al que se le aplica la sorpresa recibe la cantidad indicada por cada casa y hotel que posea."));
        this.mazo.alMazo(new Sorpresa_PorCasaHotel(-250,
                "El jugador al que se le aplica la sorpresa paga la cantidad indicada por cada casa y hotel que posea."));

        this.mazo.alMazo(new Sorpresa_PorJugador(1000,
                "El jugador a la que se le aplica la sorpresa recibe la cantidad indicada de cada jugador"));
        this.mazo.alMazo(new Sorpresa_PorJugador(-1000,
                "El jugador a la que se le aplica la sorpresa paga la cantidad indicada a cada jugador"));

        this.mazo.alMazo(new Sorpresa_SalirCarcel(this.mazo));

        this.mazo.alMazo(new Sorpresa_IrCarcel(this.tablero));
    }

    private void inicializarTablero(MazoSorpresas mazo) {

        Random rand = new Random();
        this.mazo = mazo;
        this.tablero = new Tablero(rand.nextInt(10)+1);
        
        TituloPropiedad titulo1 = new TituloPropiedad("Propiedad 1", (float)100, (float)1.1, (float)50, (float)150, (float)100);
        TituloPropiedad titulo2 = new TituloPropiedad("Propiedad 2", (float)200, (float)1.3, (float)70, (float)130, (float)75);
        TituloPropiedad titulo3 = new TituloPropiedad("Propiedad 3", (float)175, (float)1.07, (float)45, (float)170, (float)90);
        TituloPropiedad titulo4 = new TituloPropiedad("Propiedad 4", (float)95, (float)1.12, (float)55, (float)115, (float)120);

        Casilla casilla1 = new Casilla_Sorpresa("Sorpresa 1",mazo);
        Casilla casilla2 = new Casilla_Sorpresa("Sorpresa 2",mazo);
        Casilla casilla3 = new Casilla_Sorpresa( "Sorpresa 3",mazo);
        Casilla casilla4 = new Casilla_Calle(titulo1);
        Casilla casilla5 = new Casilla_Calle(titulo2);
        Casilla casilla6 = new Casilla_Calle(titulo3);
        Casilla casilla7 = new Casilla_Calle(titulo4);    
        Casilla casilla8 = new Casilla_Impuesto("Impuesto",(float)250);
        
        this.tablero.añadeCasilla(casilla7);
        this.tablero.añadeCasilla(casilla6);
        this.tablero.añadeCasilla(casilla3);
        this.tablero.añadeCasilla(casilla8);
        this.tablero.añadeJuez();
        this.tablero.añadeCasilla(casilla5);
        this.tablero.añadeCasilla(casilla2);
        this.tablero.añadeCasilla(casilla1);
        this.tablero.añadeCasilla(casilla4);
    }

    private void pasarTurno() {

        if (this.indiceJugadorActual < this.jugadores.size()-1) 
        {
            this.indiceJugadorActual++;
        } 
        else 
        {
            this.indiceJugadorActual = 0;
        }
    }

    public ArrayList<Jugador> ranking() {

        ArrayList<Jugador> ret = this.jugadores;

        Collections.sort(ret);

        return ret;
    }

    public boolean salirCarcelPagando() {

        return this.getJugadorActual().salirCarcelPagando();

    }

    public boolean salirCarcelTirando() {

        return this.getJugadorActual().salirCarcelTirando();

    }

    public OperacionesJuego siguientePaso() {

        Jugador jugadorActual = this.jugadores.get(this.indiceJugadorActual);
        OperacionesJuego operacion = this.gestorEstados.operacionesPermitidas(jugadorActual, this.estado);
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            this.pasarTurno();
            this.siguientePasoCompletado(operacion);
        } else if (operacion == OperacionesJuego.AVANZAR) {
            this.avanzaJugador();
            this.siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }

    public void siguientePasoCompletado(OperacionesJuego operacion) {

        this.estado=this.gestorEstados.siguienteEstado(this.getJugadorActual(), this.estado, operacion);

    }

    public boolean vender(int ip) {

        return this.getJugadorActual().vender(ip);

    }
}