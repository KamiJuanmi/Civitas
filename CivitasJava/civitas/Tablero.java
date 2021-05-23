package civitas;

import java.util.ArrayList;

public class Tablero {

    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;

    Tablero(int carcel)
    {
        this.numCasillaCarcel = (carcel >= 1) ? carcel : 1;
        this.porSalida = 0;
        this.tieneJuez = false;
        this.casillas = new ArrayList<>();
        Casilla salida=new Casilla("Salida");
        casillas.add(salida);
    }

    private boolean correcto() {
        return ((this.casillas.size() > this.numCasillaCarcel) && this.tieneJuez);
    }

    private boolean correcto(int casilla) {
        return this.correcto() && (casilla >= 0 && casilla <= this.casillas.size());
    }

    int getCarcel() {
        return this.numCasillaCarcel;
    }

    int getPorSalida() {
        int ret = this.porSalida;

        if (ret > 0) {
            this.porSalida--;
        }
        return ret;
    }

    void añadeCasilla(Casilla casilla) 
    {
        if (this.casillas.size() == this.numCasillaCarcel) {
            Casilla carcel = new Casilla("Cárcel");
            this.casillas.add(carcel);
            this.casillas.add(casilla);
        } 
        else 
        {
            this.casillas.add(casilla);

            if (this.casillas.size() == this.numCasillaCarcel) 
            {
                Casilla carcel = new Casilla("Cárcel");
                this.casillas.add(carcel);
            }
        }
    }

    void añadeJuez() {
        if (!this.tieneJuez) 
        {
            Casilla_Juez juez = new Casilla_Juez(this.numCasillaCarcel);
            this.añadeCasilla(juez);
            this.tieneJuez = true;
        }
    }

    Casilla getCasilla(int numCasilla) {
        
        Casilla ret = null;

        if (this.correcto(numCasilla)) 
        {
            ret = this.casillas.get(numCasilla);
        }

        return ret;
    }

    public ArrayList<Casilla> getCasillas()
    {
        return this.casillas;
    }

    int nuevaPosicion(int actual, int tirada) 
    {
        int nueva = -1;

        if (this.correcto()) {
            nueva = ((actual + tirada) >= this.casillas.size()) ? ((actual + tirada) % this.casillas.size())   : actual + tirada;
            if (nueva != actual + tirada) {
                this.porSalida++;
            }
        }

        return nueva;
    }

    int calcularTirada(int origen, int destino) 
    {
        return (origen - destino < 0) ? origen - destino + this.casillas.size() : origen - destino;
    }
}
