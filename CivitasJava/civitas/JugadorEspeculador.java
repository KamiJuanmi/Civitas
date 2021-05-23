package civitas;

import java.util.ArrayList;

public class JugadorEspeculador extends Jugador
{
    private static int FactorEspeculador=2;
    private float fianza;

    JugadorEspeculador(Jugador jugador, int fianza)
    {
        super(jugador);
        this.fianza=fianza;
        this.especulador=true;

        for(int i=0; i<jugador.getPropiedades().size(); i++)
        {
            jugador.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }

    @Override
    protected int getCasasMax()
    {
        return super.getCasasMax()*JugadorEspeculador.FactorEspeculador;
    }

    @Override
    boolean pagaImpuesto(float cantidad)
    {
        return super.paga(cantidad/2);
    }

    @Override
    protected int getHotelesMax() 
    {
        return super.getHotelesMax()*JugadorEspeculador.FactorEspeculador;
    }

    @Override
    public String toString() {

        String salida;
        salida = "Nombre: " + this.getNombre() + "\n" + "Saldo: " + this.getSaldo() + "\n" + "Casilla actual: "
                + this.getNumCasillaActual() + "\n" + "Encarcelado: " + this.isEncarcelado() + "\n" + "Este jugador es un jugador especulador.";

        return salida;
    }
    
    @Override
    protected boolean debeSerEncarcelado() 
    {
        boolean carcel = false;

        if (!this.encarcelado) {
            if (!this.tieneSalvoConducto() && getSaldo() <= this.fianza)
                carcel = true;
            else if(this.tieneSalvoConducto())
                perderSalvoConducto();
            else
                paga(fianza);
        }

        return carcel;
    }
}