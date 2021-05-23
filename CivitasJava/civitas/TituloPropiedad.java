package civitas;

public class TituloPropiedad {

    private static float factorInteresesHipoteca = (float) 1.1;
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;

    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe) {
        this.nombre = nom;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc;
        this.precioEdificar = pe;
        this.hipotecado = false;
        this.propietario = null;
        this.numCasas = 0;
        this.numHoteles = 0;
    }

    void actualizaPropietarioPorConversion(Jugador jugador) // PREGUNTAR
    {
        this.propietario = jugador;
    }

    boolean cancelarHipoteca(Jugador jugador) {
        boolean ret = false;

        if (this.hipotecado && this.esEsteElPropietario(jugador)) {
            ret = jugador.paga(
                    (float) (this.hipotecaBase * (1 + (this.getNumCasas() * 0.5) + (this.getNumHoteles() * 2.5))));
            this.hipotecado = false;
        }

        return ret;
    }

    int cantidadCasasHoteles() {
        return this.getNumCasas() + this.getNumHoteles();
    }

    boolean comprar(Jugador jugador) {
        boolean ret = false;

        if (!this.tienePropietario()) {
            this.propietario = jugador;
            propietario.paga(precioCompra);
            ret = true;
        }

        return ret;
    }

    boolean construirCasa(Jugador jugador) {
        boolean ret = false;

        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.precioEdificar);
            this.numCasas++;
            ret = true;
        }

        return ret;
    }

    boolean construirHotel(Jugador jugador) {
        boolean ret = false;

        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.precioEdificar);
            this.numHoteles++;
            ret = true;
        }

        return ret;
    }

    boolean derruirCasas(int n, Jugador jugador) {
        boolean ret = false;

        if (this.esEsteElPropietario(jugador) && (n <= this.getNumCasas())) {
            this.numCasas -= n;
            ret = true;
        }

        return ret;
    }

    private boolean esEsteElPropietario(Jugador jugador) {

        return jugador == this.propietario;

    }

    public boolean getHipotecado() {

        return this.hipotecado;

    }

    float getImporteCancelarHipoteca() {
        return (float) ((this.hipotecaBase * (1 + (this.getNumCasas() * 0.5) + (this.getNumHoteles() * 2.5)))
                * TituloPropiedad.factorInteresesHipoteca);
    }

    private float getImporteHipoteca() {
        return this.hipotecaBase;
    }

    public String getNombre() {

        return this.nombre;

    }

    public int getNumCasas() {

        return this.numCasas;

    }

    public int getNumHoteles() {

        return this.numHoteles;

    }

    private float getPrecioAlquiler() {
        float alq = 0;

        if (!propietarioEncarcelado() && !this.hipotecado) {
            alq = (float) (this.alquilerBase * (1 + (this.getNumCasas() * 0.5) + (this.getNumHoteles() * 2.5)));
        }

        return alq;
    }

    float getPrecioCompra() {
        return this.precioCompra;
    }

    float getPrecioEdificar() {
        return this.precioEdificar;
    }

    float getPrecioVenta() {
        return this.precioCompra
                + (this.numCasas + 5 * this.numHoteles) * this.factorRevalorizacion * this.precioEdificar;
    }

    Jugador getPropietario() {

        return this.propietario;

    }

    boolean hipotecar(Jugador jugador) {
        boolean ret = false;

        if (!this.hipotecado && this.esEsteElPropietario(jugador)) {
            this.propietario.recibe(
                    (float) (this.hipotecaBase * (1 + (this.getNumCasas() * 0.5) + (this.getNumHoteles() * 2.5))));
            this.hipotecado = true;
            ret = true;
        }

        return ret;
    }

    private boolean propietarioEncarcelado() {

        return this.propietario.isEncarcelado();

    }

    boolean tienePropietario() {

        return this.propietario != null;

    }

    public String toString() {
        String str = "\nNombre: " + this.nombre + "\nNúmero de casas " + this.numCasas + "\nNúmero de hoteles: "
                + this.numHoteles + "\nPrecio de compra: " + this.precioCompra + "\nPropietario: ";

        if (this.tienePropietario())

            str += this.propietario.getNombre() + "\n";

        else

            str += "sin propietario\n";

        return str;
    }

    void tramitarAlquiler(Jugador jugador) {

        if (this.propietario != null && !this.esEsteElPropietario(jugador)) 
        {
            jugador.pagaAlquiler(this.getPrecioAlquiler());
            this.propietario.recibe(this.getPrecioAlquiler());
        }

    }

    boolean vender(Jugador jugador) {
        boolean venta = false;

        if (this.esEsteElPropietario(jugador)) {

            jugador.recibe(this.precioCompra
                    + (this.numCasas + 5 * this.numHoteles) * this.precioEdificar * this.factorRevalorizacion);
            this.propietario = null;
            this.numCasas = 0;
            this.numHoteles = 0;
            venta = true;

        }

        return venta;
    }
}