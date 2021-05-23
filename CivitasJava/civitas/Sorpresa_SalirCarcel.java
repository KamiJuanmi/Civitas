package civitas;

import java.util.ArrayList;

class Sorpresa_SalirCarcel extends Sorpresa
{
    private MazoSorpresas mazo;

    Sorpresa_SalirCarcel(MazoSorpresas mazo)
    {
        super("Queda libre de la cárcel.");
        this.mazo=mazo;
    }

    @Override
    protected void aplicarAJugador(int actual, ArrayList<Jugador> todos)
    {
        if(super.jugadorCorrecto(actual, todos))
        {
            informe(actual, todos);
            boolean obtiene=true;

            for(int i=0; i<todos.size()&&!obtiene; i++)
            {
                if(todos.get(i).tieneSalvoConducto())
                {
                    obtiene=false;
                }
            }

            if(obtiene)
            {
                todos.get(actual).obtenerSalvoConducto(this);
                salirDelMazo();
            }
        }
    }

    void salirDelMazo()
    {
        mazo.inhabilitarCartaEspecial(this);   
    }

    void usada()
    {
        mazo.habilitarCartaEspecial(this);   
    }

    @Override
    public String toString()
    {
        return "Sorpresa de salva de la cárcel.";
    }
}