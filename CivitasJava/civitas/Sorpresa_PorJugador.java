package civitas;

import java.util.ArrayList;

public class Sorpresa_PorJugador extends Sorpresa
{
    private int valor;

    Sorpresa_PorJugador(int valor, String texto)
    {
        super(texto);
        this.valor=valor;
    }

    @Override
    protected void aplicarAJugador(int actual, ArrayList<Jugador> todos)
    {
        if(super.jugadorCorrecto(actual, todos))
        {
            super.informe(actual, todos);
            Sorpresa_PagarCobrar pagar=new Sorpresa_PagarCobrar(-valor, "EL jugador al que se le aplica esta sorpresa ha de pagar el importe indicado");
            Sorpresa_PagarCobrar cobrar=new Sorpresa_PagarCobrar(valor*(todos.size()-1), "El jugador al que se le aplica esta sorpresa recibe el importe indicado.");
        
            for(int i=0; i<todos.size(); i++)
            {
                if(i!=actual)
                {
                    pagar.aplicarAJugador(i, todos); 
                }
            }

            cobrar.aplicarAJugador(actual, todos);
        }
    }

    @Override
    public String toString()
    {
        return this.texto;
    }
}