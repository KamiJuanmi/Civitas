package civitas;

import java.util.ArrayList;

public class Casilla_Sorpresa extends Casilla
{
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    Casilla_Sorpresa(String nombre,MazoSorpresas mazo) { 

        super(nombre);
        this.mazo = mazo;
        this.sorpresa=null;
    }
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {

        if (super.jugadorCorrecto(iactual, todos)) 
        {
            this.sorpresa = this.mazo.siguiente();
            this.sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    @Override 
    public String toString()
    {
        String ret=super.getNombre();
        ret += " de tipo sorpresa : \n";
        if(this.sorpresa!=null){
            ret += this.sorpresa.toString();
        }
        return ret;
    }
}