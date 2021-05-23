package civitas;

import java.util.ArrayList;

public class Casilla {

    private String nombre;

    Casilla(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getNombre() 
    {
        return this.nombre;
    }
    protected void informe(int iactual, ArrayList<Jugador> todos) 
    {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " ha caido en la casilla " + this.toString() + "\n");
    }

    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos) 
    {
        return iactual >= 0 && iactual < todos.size();
    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos)
    {
        if(this.jugadorCorrecto(iactual, todos))
        {
            this.informe(iactual, todos);
        }
    }

    public String toString() 
    {
        String ret = this.nombre + " de tipo descanso.\n";
        return ret;
    }
}