package civitas;

import java.util.ArrayList;

class Casilla_Calle extends Casilla
{
    private TituloPropiedad titulo;
    private float importe;

    Casilla_Calle(TituloPropiedad titulo)
    {
        super(titulo.getNombre());
        this.titulo=titulo;
        this.importe=titulo.getPrecioCompra();
    }

    TituloPropiedad getTituloPropiedad() 
    {
        return this.titulo;
    }
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos)
    {   
        if (super.jugadorCorrecto(iactual, todos)) 
        {
            super.informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
            if (!this.titulo.tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                this.titulo.tramitarAlquiler(jugador);
            }
        }
    }

    @Override
    public String toString() 
    {
        String ret = getNombre() + " de tipo calle \n" + this.titulo.toString();
        return ret;
    }
}