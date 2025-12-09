package Principal;

public class Solicitud {

    private int identificador;
    private TipoSolicitud tipoSolicitud;
    private Hospedaje hospedaje;
    private BoletoAvion boleto;
    private int dias;

    public Solicitud(int identificador, TipoSolicitud tipoSolicitud,
                     Hospedaje hospedaje, BoletoAvion boleto, int dias) {

        this.identificador = identificador;
        this.tipoSolicitud = tipoSolicitud;
        this.hospedaje = hospedaje;
        this.boleto = boleto;
        this.dias = dias;
    }

    public double calcularTotal() {
        return hospedaje.calcularCosto() + boleto.calcularCosto();
    }

    @Override
    public String toString() {
        return "\n--- SOLICITUD DE COMISIÓN ---\n" +
                "Identificador: " + identificador +
                "\nTipo de Solicitud: " + tipoSolicitud +
                "\nDías de Comisión: " + dias +
                "\n\n--- COSTOS ---\n" +
                hospedaje +
                "\n" + boleto +
                "\n\nTOTAL: $" + calcularTotal();
    }
}
