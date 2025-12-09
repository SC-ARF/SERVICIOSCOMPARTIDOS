package Principal;

public class BoletoAvion {

    private boolean requiereBoleto;

    public BoletoAvion(boolean requiereBoleto) {
        this.requiereBoleto = requiereBoleto;
    }

    public double calcularCosto() {
        if (!requiereBoleto) return 0;
        return 3500; // Ejemplo, precio de un vuelo
    }

    @Override
    public String toString() {
        return "Requiere Boleto de Avión: " + requiereBoleto +
                "\nCosto Boleto: $" + calcularCosto();
    }
}