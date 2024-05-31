package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;

import java.util.ArrayList;

public class ColaboracionesRealizadas {
    private static double CONSTANTE_PESOS_DONADOS = 0.5;
    private static double CONSTANTE_HELADERAS = 5;
    private static double CONSTANTE_VIANDAS_DISTRIBUIDAS = 1;
    private static double CONSTANTE_VIANDAS_DONADAS = 1.5;
    private static double CONSTANTE_TARJETAS = 2;
    private double tarjetasRepartidas;
    private ArrayList<DonacionDeDinero> donacionesDeDineroRealizadas;
    private double viandasDistribuidas;
    private double viandasDonadas;
    private ArrayList<Heladera> heladerasDonadas;

    public ColaboracionesRealizadas() {
        this.tarjetasRepartidas = 0;
        this.donacionesDeDineroRealizadas = new ArrayList<>();
        this.viandasDistribuidas = 0;
        this.viandasDonadas = 0;
        this.heladerasDonadas = new ArrayList<>();
    }


    public void agregarViandasDistribuidas(int cantidadViandas) {
        this.viandasDistribuidas += cantidadViandas;
    }

    public void agregarDonacionDeDinero(DonacionDeDinero donacion) {
        this.donacionesDeDineroRealizadas.add(donacion);
    }

    public void agregarViandaDonada() {
        this.viandasDonadas += 1;
    }

    public void agregarHeladera(Heladera heladera) {
        this.heladerasDonadas.add(heladera);
    }

    public void aumentarTarjetasRepartidas() {
        this.tarjetasRepartidas += 1;
    }

    public double cantidadDonada(DonacionDeDinero donacion){
        return donacion.cantidadDonada();
    }

    public Integer cantHeladerasActivas(){
        return Math.toIntExact(heladerasDonadas.stream().filter(Heladera::isActiva).count());
    }

    public Integer sumatoriaDeMesesActiva(){
        return heladerasDonadas.stream().mapToInt(Heladera::mesesActiva).sum();
    }

    public double pesosDonados (){
        return donacionesDeDineroRealizadas.stream().mapToDouble(this::cantidadDonada).sum();
    }

    public double calcularPuntaje(){
        return CONSTANTE_PESOS_DONADOS * this.pesosDonados() +
                CONSTANTE_HELADERAS * this.cantHeladerasActivas() * this.sumatoriaDeMesesActiva() +
                CONSTANTE_VIANDAS_DISTRIBUIDAS * viandasDistribuidas +
                CONSTANTE_VIANDAS_DONADAS * viandasDonadas +
                CONSTANTE_TARJETAS * tarjetasRepartidas;
    }
}
