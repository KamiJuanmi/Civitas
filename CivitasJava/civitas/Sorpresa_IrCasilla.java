package civitas;

import java.util.ArrayList;

public class Sorpresa_IrCasilla extends Sorpresa
{
    private int valor;
    private Tablero tablero;

    Sorpresa_IrCasilla(Tablero tablero,int valor, String texto)
    {
        super(texto);
        this.tablero=tablero;
        this.valor=valor;
    }

    @Override
    protected void aplicarAJugador(int actual, ArrayList<Jugador> todos)
    {
        if(super.jugadorCorrecto(actual, todos))
        {
            super.informe(actual,todos);

            int casillaActual=todos.get(actual).getNumCasillaActual();
            int tirada=tablero.calcularTirada(casillaActual, valor);
            todos.get(actual).moverACasilla(tablero.nuevaPosicion(casillaActual, tirada));
            tablero.getCasilla(valor).recibeJugador(actual, todos);
        }
    }

    @Override
    public String toString()
    {
        return super.texto;
    }
}