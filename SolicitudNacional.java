
public class SolicitudNacional extends Solicitud {

    private static final double IMPUESTO = 0.16; // ejemplo: IVA sobre algunos rubros

    public SolicitudNacional(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        super(id, tipo, hospedaje, boleto, viaticos, dias);
    }

    @Override
    public double calcularTotal() {
        double subtotal = hospedaje.calcularCosto() + boleto.calcularCosto() + viaticos.calcularCosto();
        double impuesto = subtotal * IMPUESTO;
        return subtotal + impuesto;
    }
}