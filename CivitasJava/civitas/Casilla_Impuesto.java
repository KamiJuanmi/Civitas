
package civitas;

import java.util.ArrayList;

public class Casilla_Impuesto extends Casilla
{
    private float importe;

    Casilla_Impuesto(String nombre, float cantidad)
    {
        super(nombre);
        this.importe=cantidad;
    }

    public float getImporte() 
    {
        return this.importe;
    }
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos)
    {   
        if (super.jugadorCorrecto(iactual, todos)) {
            super.informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(this.importe);
        }
    }
    @Override 
    public String toString()
    {
        String ret=super.getNombre();
        ret += " de tipo impuesto con precio: \n" + this.importe;
        return ret;
    }
}


