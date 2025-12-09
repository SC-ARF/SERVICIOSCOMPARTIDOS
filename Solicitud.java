package principal.models;

public abstract class Solicitud {
    protected int id;
    protected TipoSolicitud tipo;
    protected Hospedaje hospedaje;
    protected BoletoAvion boleto;
    protected Viaticos viaticos;
    protected int dias;

    public Solicitud(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        this.id = id;
        this.tipo = tipo;
        this.hospedaje = hospedaje;
        this.boleto = boleto;
        this.viaticos = viaticos;
        this.dias = dias;
    }

    public abstract double calcularTotal();

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Solicitud ID=" + id + "\nTipo=" + tipo + "\nDías=" + dias + "\n" + hospedaje + "\n" + boleto + "\n" + viaticos + "\nTOTAL=$" + String.format("%.2f", calcularTotal());
    }
}