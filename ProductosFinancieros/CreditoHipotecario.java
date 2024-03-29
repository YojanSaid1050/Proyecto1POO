class CreditoHipotecario extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;

    public CreditoHipotecario(int numeroCuenta, double montoPrestamo, double tasaInteres) {
        super(numeroCuenta);
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
    }
    public double getMontoPrestamo() {
        return montoPrestamo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }
}