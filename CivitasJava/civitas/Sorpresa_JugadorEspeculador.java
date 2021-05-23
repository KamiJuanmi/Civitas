package civitas;

import java.util.ArrayList;

public class Sorpresa_JugadorEspeculador extends Sorpresa
{
    private int fianza;

    Sorpresa_JugadorEspeculador(int fianza,String texto)
    {
        super(texto);
        this.fianza=fianza;
        
    }

    @Override
    protected void aplicarAJugador(int actual, ArrayList<Jugador> todos)
    {
        if(super.jugadorCorrecto(actual, todos))
        {   
            super.informe(actual, todos);
            todos.set(actual, new JugadorEspeculador(todos.get(actual),this.fianza));
        }
    }

    @Override
    public String toString()
    {
        return this.texto;
    }
}