
import principal.models.*;

public class SolicitudFactory {

    // Tarifas base (pueden venir de config)
    private static final double TARIFA_HOSPEDAJE_NACIONAL = 900.0;
    private static final double TARIFA_HOSPEDAJE_INTERNACIONAL = 1800.0; // por noche

    private static final double BOLETO_NACIONAL_PROM = 3200.0;
    private static final double BOLETO_INTERNACIONAL_PROM = 15000.0;

    private static final double VIATICO_DIARIO_NAC = 500.0;
    private static final double VIATICO_DIARIO_INTL = 1200.0;

    public static Solicitud crearSolicitud(int id, TipoSolicitud tipo, boolean requiereHospedaje, boolean requiereBoleto,
                                           boolean internacional, int dias) {

        double tarifaHospedaje = internacional ? TARIFA_HOSPEDAJE_INTERNACIONAL : TARIFA_HOSPEDAJE_NACIONAL;
        Hospedaje h = new Hospedaje(requiereHospedaje, dias, tarifaHospedaje);

        double tarifaBoleto = 0.0;
        if (requiereBoleto) tarifaBoleto = internacional ? BOLETO_INTERNACIONAL_PROM : BOLETO_NACIONAL_PROM;
        BoletoAvion b = new BoletoAvion(requiereBoleto, internacional, tarifaBoleto);

        double montoViatico = internacional ? VIATICO_DIARIO_INTL : VIATICO_DIARIO_NAC;
        Viaticos v = new Viaticos(dias, montoViatico);

        if (internacional) {
            return new SolicitudInternacional(id, tipo, h, b, v, dias);
        } else {
            return new SolicitudNacional(id, tipo, h, b, v, dias);
        }
    }
}