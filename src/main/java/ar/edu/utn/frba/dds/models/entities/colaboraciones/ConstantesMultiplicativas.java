package ar.edu.utn.frba.dds.models.entities.colaboraciones;

public class ConstantesMultiplicativas {
    private static double CONSTANTE_PESOS_DONADOS = 0.5;
    private static double CONSTANTE_HELADERAS = 5;
    private static double CONSTANTE_VIANDAS_DISTRIBUIDAS = 1;
    private static double CONSTANTE_VIANDAS_DONADAS = 1.5;
    private static double CONSTANTE_TARJETAS = 2;

    public double getCtePesosDonados() {
        return CONSTANTE_PESOS_DONADOS;
    }

    public double getCteHeladeras() {
        return CONSTANTE_HELADERAS;
    }

    public double getCteViandasDistribuidas() {
        return CONSTANTE_VIANDAS_DISTRIBUIDAS;
    }

    public double getCteViandasDonadas() {
        return CONSTANTE_VIANDAS_DONADAS;
    }

    public double getCteTarjetas() {
        return CONSTANTE_TARJETAS;
    }


}
