package civitas;

import java.util.ArrayList;

public class Sorpresa_PorCasaHotel extends Sorpresa
{
    private int valor;

    Sorpresa_PorCasaHotel(int valor, String texto)
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
            todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
        }
    }

    @Override
    public String toString()
    {
        return this.texto;
    }
}