public class Viaticos {
    private int dias;
    private double montoDiario;

    public Viaticos(int dias, double montoDiario) {
        this.dias = dias;
        this.montoDiario = montoDiario;
    }

    public double calcularCosto() {
        return dias * montoDiario;
    }

    @Override
    public String toString() {
        return "Viáticos: dias=" + dias + ", monto diario=$" + String.format("%.2f", montoDiario) + ", total=$" + String.format("%.2f", calcularCosto());
    }
}