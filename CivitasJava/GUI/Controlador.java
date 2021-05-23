package GUI;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;
import civitas.OperacionInmobiliaria;
import civitas.GestionesInmobiliarias;

public class Controlador
{
    private CivitasJuego juego;
    private CivitasView vista;

    public Controlador(CivitasJuego juego, CivitasView vista)
    {
        this.juego=juego;
        this.vista=vista;
    }

    public void juega()
    {
        vista.setCivitasJuego(juego);

        while(!juego.finalDelJuego())
        {
            vista.actualizaVista();
            //vista.pausa();
            OperacionesJuego operacion=juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);

            if(operacion!=OperacionesJuego.PASAR_TURNO)
            {
                vista.mostrarEventos();
            }

            if(!juego.finalDelJuego())
            {
                switch(operacion)
                {
                    case COMPRAR:
                       
                        if(vista.comprar()==Respuestas.values()[1])
                        {
                            juego.comprar();
                        }
                            
                        juego.siguientePasoCompletado(operacion);
                    break;

                    case GESTIONAR:

                        vista.gestionar();
                        int i=vista.getGestion();
                        int j=vista.getPropiedad();
                       
                        OperacionInmobiliaria op_inm=new OperacionInmobiliaria(GestionesInmobiliarias.values()[i], j);

                        switch(op_inm.getGestion())
                        {
                            case VENDER:
                                juego.vender(op_inm.getNumPropiedad());
                            break;

                            case HIPOTECAR:
                                juego.hipotecar(op_inm.getNumPropiedad());
                            break;

                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(op_inm.getNumPropiedad());
                            break;

                            case CONSTRUIR_CASA:
                                juego.construirCasa(op_inm.getNumPropiedad());
                            break;

                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(op_inm.getNumPropiedad());
                            break;

                            case TERMINAR:
                                juego.siguientePasoCompletado(operacion);
                                break;
                        }
                    break;
                    
                    case SALIR_CARCEL:
                        
                        if(vista.salirCarcel()==0)
                        {
                            juego.salirCarcelPagando();
                        }
                        else
                        {
                            juego.salirCarcelTirando();
                        }

                        juego.siguientePasoCompletado(operacion);
                    break;

                }
            }
        }

        ArrayList<Jugador> rank=juego.ranking();
        for(int i=0;i<rank.size();i++)
        {
            System.out.println(rank.get(i).toString());
        }
    }
}