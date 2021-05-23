package civitas;

import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {

    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;

    private void init() {
        this.barajada = false;
        this.usadas = 0;
        this.sorpresas = new ArrayList<>();
        this.cartasEspeciales = new ArrayList<>();
    }

    MazoSorpresas(boolean debug) {
        this.debug = debug;
        init();

        if (debug) {
            Diario.getInstance().ocurreEvento("El mazo de sorpresas se encuentra en modo debug.\n");
        }
    }

    MazoSorpresas() {
        this.debug = false;
        init();
    }

    void alMazo(Sorpresa s) {
        if (!this.barajada) {
            sorpresas.add(s);
        }
    }

    Sorpresa siguiente() {
        if ((!this.barajada || this.usadas == this.sorpresas.size()) && !this.debug) {
            Collections.shuffle(this.sorpresas);
            this.barajada = true;
            this.usadas = 0;
        }

        Sorpresa ret=this.sorpresas.get(0);
        Collections.swap(this.sorpresas,0,sorpresas.size()-1);
        this.ultimaSorpresa=ret;
        this.usadas++;
        return ret;
    }

    void inhabilitarCartaEspecial(Sorpresa sorpresa) {
        if (this.sorpresas.contains(sorpresa)) {
            this.sorpresas.remove(sorpresa);
            this.cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha inhabilitado una sorpresa.\n");
        }
    }

    void habilitarCartaEspecial(Sorpresa sorpresa) {
        if (this.cartasEspeciales.contains(sorpresa)) {
            this.sorpresas.add(sorpresa);
            this.cartasEspeciales.remove(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha habilitado una sorpresa.\n");
        }
    }
}
