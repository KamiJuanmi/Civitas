package civitas;

import java.util.ArrayList;
import GUI.Dado;

public class Jugador implements Comparable<Jugador> {

    // ATRIBUTOS

    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    private Sorpresa_SalirCarcel salvoconducto;
    private ArrayList<TituloPropiedad> propiedades;
    protected Boolean especulador;

    // CONSTRUCTORES

    Jugador(String name) {
        this.nombre = name;
        this.saldo = Jugador.SaldoInicial;
        this.encarcelado = false;
        this.puedeComprar = true;
        this.salvoconducto = null;
        this.propiedades = new ArrayList<>();
        this.especulador=false;
    }

    protected Jugador(Jugador otro) {
        this.nombre = otro.getNombre();
        this.saldo = otro.getSaldo();
        this.encarcelado = otro.isEncarcelado();
        this.puedeComprar = otro.getPuedeComprar();
        this.propiedades = otro.getPropiedades();
        this.salvoconducto = otro.salvoconducto;
        this.especulador=otro.especulador;
    }

    // METODOS GET Y SET

    protected int getCasasMax() {
        return CasasMax;
    }
    
    public boolean isEspeculador()
    {
        return this.especulador;
    }

    int getCasasPorHotel() {
        return CasasPorHotel;
    }

    protected int getHotelesMax() {
        return HotelesMax;
    }

    public String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual ;
    }

    private float getPrecioLibertad() {
        return PrecioLibertad;
    }

    private float getPremioPasoSalida() {
        return PasoPorSalida;
    }

    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    public ArrayList<String> getPropiedadesString() {
        ArrayList<String> prop = new ArrayList<>();

        for (int i = 0; i < this.propiedades.size(); i++) {
            prop.add(propiedades.get(i).getNombre());
        }

        return prop;
    }

    boolean getPuedeComprar() {
        return puedeComprar;
    }

    public float getSaldo() {
        return saldo;
    }

    // METODOS

    boolean cancelarHipoteca(int ip) {
        boolean result = false;
        if (!this.encarcelado) {
            if (this.existeLaPropiedad(ip)) {
                TituloPropiedad propiedad = this.propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                boolean puedoGastar = this.puedoGastar(cantidad);
                if (puedoGastar) {
                    result = propiedad.cancelarHipoteca(this);
                    if (result) {
                        Diario.getInstance().ocurreEvento(
                                "El jugador " + this.nombre + " cancela la hipoteca de la propiedad " + ip + "\n");
                    }
                }
            }
        }
        return result;
    }

    int cantidadCasasHoteles() {

        int sal = 0;

        for (int i = 0; i < this.propiedades.size(); i++) {
            sal += propiedades.get(i).cantidadCasasHoteles();
        }

        return sal;
    }

    @Override
    public int compareTo(Jugador otro) {

        return Float.compare(this.getSaldo(), otro.getSaldo());
    }

    boolean comprar(TituloPropiedad titulo) {
        boolean result = false;
        if (!this.encarcelado) {
            if (this.puedeComprar) {
                float precio = titulo.getPrecioCompra();
                if (this.puedoGastar(precio)) {
                    result = titulo.comprar(this);
                    if (result) {
                        this.propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento(
                                "El jugador " + this.nombre + " compra la propiedad " + titulo.toString()+ "\n");
                    }
                    this.puedeComprar = false;
                }
            }
        }
        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false;
        boolean puedoEdificarCasa = false;
        if (!this.encarcelado) {
            boolean existe = this.existeLaPropiedad(ip);
            if (existe) {
                TituloPropiedad propiedad = this.propiedades.get(ip);
                puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
                if (puedoEdificarCasa) {
                    result = propiedad.construirCasa(this);
                    if (result) {
                        Diario.getInstance()
                                .ocurreEvento("El jugador " + nombre + " construye casa en la propiedad " + ip+ "\n");
                    }
                }

            }
        }
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;
        if (!this.encarcelado) {
            if (this.existeLaPropiedad(ip)) {
                TituloPropiedad propiedad = this.propiedades.get(ip);
                boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
                if (puedoEdificarHotel) {
                    result = propiedad.construirHotel(this);
                    int casasPorHotel = this.getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel, this);
                    Diario.getInstance()
                            .ocurreEvento("El jugador " + nombre + " construye hotel en la propiedad " + ip+ "\n");

                }
            }
        }
        return result;
    }

    protected boolean debeSerEncarcelado() {

        boolean carcel = false;

        if (!this.encarcelado) {
            if (!this.tieneSalvoConducto())
                carcel = true;
            else
                this.perderSalvoConducto();
        }

        return carcel;
    }

    boolean enBancarrota() {

        return this.getSaldo() <= 0;
    }

    boolean encarcelar(int numCasillaCarcel) {

        if (this.debeSerEncarcelado()) {
            this.moverACasilla(numCasillaCarcel);
            this.encarcelado = true;
            Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " ha sido encarcelado.\n");
        }

        return this.encarcelado;
    }

    private boolean existeLaPropiedad(int ip) {

        return ip >= 0 && ip < propiedades.size();
    }

    boolean hipotecar(int ip) {
        boolean result = false;
        if (!this.encarcelado) {
            if (this.existeLaPropiedad(ip)) {
                TituloPropiedad propiedad = this.propiedades.get(ip);
                result = propiedad.hipotecar(this);
            }
            if (result) {
                Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " hipoteca la propiedad " + ip+ "\n");
            }
        }
        return result;
    }

    public boolean isEncarcelado() {

        return this.encarcelado;
    }

    boolean modificarSaldo(float cantidad) {

        this.saldo += cantidad;

        Diario.getInstance().ocurreEvento(
                "Se ha modificado el saldo del jugador " + this.getNombre() + " en: " + cantidad + " unidades.\n");

        return true;
    }

     boolean moverACasilla(int numCasilla) {

        boolean ret = false;

        if (!this.isEncarcelado()) {
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " se mueve hasta la casilla " + numCasilla+ "\n");
            ret = true;
        }

        return ret;
    }

    boolean obtenerSalvoConducto(Sorpresa_SalirCarcel sorpresa) {

        if (this.encarcelado)
            this.salvoconducto = sorpresa;

        return (!this.encarcelado);
    }

    boolean paga(float cantidad) {

        return modificarSaldo(cantidad * -1);

    }

    boolean pagaAlquiler(float cantidad) {

        return this.pagaImpuesto(cantidad);
    }

    boolean pagaImpuesto(float cantidad) {

        return this.encarcelado ? false : this.paga(cantidad);

    }

    boolean pasaPorSalida() {

        this.modificarSaldo(Jugador.PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " pasa por la salida y se le incrementa el saldo en: " + Jugador.PasoPorSalida + " rupias.\n");
        return true;
    }

    protected void perderSalvoConducto() {

        this.salvoconducto.usada();
        this.salvoconducto = null;
    }

    boolean puedeComprarCasilla() {
        
        this.puedeComprar = !this.encarcelado;
        return this.puedeComprar;
    }

    private boolean puedeSalirCarcelPagando() {

        return this.getSaldo() >= Jugador.PrecioLibertad;
    }

    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        return this.getSaldo() >= propiedad.getPrecioEdificar();
    }

    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean puedoEdificarHotel = false;
        float precio = propiedad.getPrecioEdificar();
        if (this.puedoGastar(precio)) {
            if (propiedad.getNumHoteles() < this.getHotelesMax()) {
                if (propiedad.getNumCasas() >= this.getCasasPorHotel()) {
                    puedoEdificarHotel = true;
                }
            }
        }
        return puedoEdificarHotel;
    }

    private boolean puedoGastar(float precio) {

        return this.encarcelado ? false : this.saldo >= precio;

    }

    boolean recibe(float cantidad) {

        boolean ret = false;
        if (!this.isEncarcelado()) {
            ret = this.modificarSaldo(cantidad);
        }

        return ret;
    }

    boolean salirCarcelPagando() {

        boolean salida = false;

        if (this.encarcelado && this.puedeSalirCarcelPagando()) {

            this.paga(Jugador.PrecioLibertad);
            this.encarcelado = false;
            salida = true;
            Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " paga para salir de la carcel.\n");

        }

        return salida;

    }

    boolean salirCarcelTirando() {

        if (Dado.getInstance().salgoDeLaCarcel()) {
            this.encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " sale de la carcel.\n");
        }

        return this.isEncarcelado();
    }

    boolean tieneAlgoQueGestionar() {

        return this.propiedades.size() > 0;

    }

    boolean tieneSalvoConducto() {

        return this.salvoconducto != null;

    }

    @Override
    public String toString() {

        String salida;
        salida = "Nombre: " + this.getNombre() + "\n" + "Saldo: " + this.getSaldo() + "\n" + "Casilla actual: "
                + this.getNumCasillaActual() + "\n" + "Encarcelado: " + this.isEncarcelado()+"\n";

        return salida;
    }

    boolean vender(int ip) {

        boolean ret = false;

        if (!this.isEncarcelado() && this.existeLaPropiedad(ip)) {
            if (this.propiedades.get(ip).vender(this)) {
                this.propiedades.remove(ip);
                Diario.getInstance().ocurreEvento("Se ha vendido una propiedad.\n");
                ret = true;
            }
        }

        return ret;
    }
}