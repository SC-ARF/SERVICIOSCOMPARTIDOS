package principal.models;

public class BoletoAvion {
    private boolean requiere;
    private boolean internacional;
    private double tarifa; // tarifa calculada

    public BoletoAvion(boolean requiere, boolean internacional, double tarifa) {
        this.requiere = requiere;
        this.internacional = internacional;
        this.tarifa = tarifa;
    }

    public double calcularCosto() {
        if (!requiere) return 0.0;
        return tarifa;
    }

    @Override
    public String toString() {
        return "BoletoAvion: requiere=" + requiere + ", internacional=" + internacional + ", costo=$" + String.format("%.2f", calcularCosto());
    }
}