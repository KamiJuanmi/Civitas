package civitas;

import java.util.ArrayList;

public class Casilla_Juez extends Casilla
{
    private static int carcel;

    Casilla_Juez(int numCasillaCarcel) 
    {
        super("Juez");
        Casilla_Juez.carcel = numCasillaCarcel;
    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos)
    {
        if (this.jugadorCorrecto(iactual, todos))
        {
            super.informe(iactual, todos);
            todos.get(iactual).encarcelar(Casilla_Juez.carcel);
        }
    }

    @Override
    public String toString() 
    {
        return "Casilla del juez.";
    }
}