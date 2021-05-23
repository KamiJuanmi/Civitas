package civitas;

import java.util.ArrayList;

class Sorpresa_IrCarcel extends Sorpresa
{
    private Tablero tablero;

    Sorpresa_IrCarcel(Tablero tablero)
    {
        super("Vaya a la cárcel.");
        this.tablero=tablero;
    }

    @Override
    protected void aplicarAJugador(int actual, ArrayList<Jugador> todos)
    {
        if(super.jugadorCorrecto(actual, todos))
        {
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    @Override
    public String toString()
    {
        return "Sorpresa que envía a la cárcel.";
    }
}