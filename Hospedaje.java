package principal.models;

public class Hospedaje {
    private boolean requiere;
    private int dias;
    private double tarifaPorDia; // tarifa base

    public Hospedaje(boolean requiere, int dias, double tarifaPorDia) {
        this.requiere = requiere;
        this.dias = dias;
        this.tarifaPorDia = tarifaPorDia;
    }

    public double calcularCosto() {
        if (!requiere) return 0.0;
        return dias * tarifaPorDia;
    }

    public boolean isRequiere() { return requiere; }
    public int getDias() { return dias; }

    @Override
    public String toString() {
        return "Hospedaje: requiere=" + requiere + ", dias=" + dias + ", costo=$" + String.format("%.2f", calcularCosto());
    }
}