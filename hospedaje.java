package Principal;

public class Hospedaje {

    private boolean requiereHospedaje;
    private int dias;

    public Hospedaje(boolean requiereHospedaje, int dias) {
        this.requiereHospedaje = requiereHospedaje;
        this.dias = dias;
    }

    public double calcularCosto() {
        if (!requiereHospedaje) return 0;
        double costoPorDia = 800;  // ejemplo
        return dias * costoPorDia;
    }

    @Override
    public String toString() {
        return "Requiere Hospedaje: " + requiereHospedaje +
                "\nCosto Hospedaje: $" + calcularCosto();
    }
}
