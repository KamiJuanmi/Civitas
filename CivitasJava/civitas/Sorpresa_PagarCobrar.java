package civitas;

import java.util.ArrayList;

class Sorpresa_PagarCobrar extends Sorpresa
{
    private int valor;
    
    Sorpresa_PagarCobrar(int valor, String texto)
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
            todos.get(actual).modificarSaldo(valor);
        }
    }

    @Override
    public String toString()
    {
        return this.texto;
    }
}