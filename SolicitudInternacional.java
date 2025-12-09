
public class SolicitudInternacional extends Solicitud {

    private static final double TASA_CAMBIO = 18.5; // ejemplo: para convertir USD a MXN (si aplica)
    private static final double RECARGO_INTERNACIONAL = 0.12; // recargo por trámites

    public SolicitudInternacional(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        super(id, tipo, hospedaje, boleto, viaticos, dias);
    }

    @Override
    public double calcularTotal() {
        double subtotal = hospedaje.calcularCosto() + boleto.calcularCosto() + viaticos.calcularCosto();
        double recargo = subtotal * RECARGO_INTERNACIONAL;
        return subtotal + recargo; // supongamos que ya está en moneda local
    }
}