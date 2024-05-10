package ar.edu.utn.frba.dds.colaboraciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reconocimiento {
    private double ctePesosDonados = 0.5;
    private double cteHeladeras = 5;
    private double cteViandasDistribuidas = 1;
    private double cteViandasDonadas = 1.5;
    private double cteTarjetas = 2;
    private static Reconocimiento instance = null;

    private Reconocimiento() {
    }
    public static Reconocimiento getInstance() {
        if (instance == null) {
            instance = new Reconocimiento();
        }
        return instance;
    }

}
