package ar.edu.utn.frba.dds.seguridad;

import ar.edu.utn.frba.dds.utils.seguridad.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ValidadorDeContraseniasTest {

    private ValidadorDeContrasenias validador;

    @BeforeEach
    public void init(){
        validador = new ValidadorDeContrasenias();
        validador.agregarCondiciones(new CumpleLongitud(8,64),
                                     new TieneMayuscula(),
                                     new TieneMinuscula(),
                                     new TieneNumero(),
                                     new TieneCaracterEspecial(),
                                     new NoEstaDentroDeLasComunes());
    }

    @Test
    @DisplayName("Contraseña aceptada por cumplir con los requisitos")
    public void contraseniaAceptadaTest() throws IOException {
        Assertions.assertTrue(validador.esValida("DDS-Martes-Mañana-@2024"));

    }

    @Test
    @DisplayName("Contraseña rechazada por longitud muy corta")
    public void contraseniaNoCumpleConLongitudTest() throws IOException {
        Assertions.assertFalse(validador.esValida("DDS"));
    }

    @Test
    @DisplayName("Contraseña rechazada ya que se encuentra dentro de las 10 mil más usadas")
    public void contraseniaEsInsegura() throws IOException {
        Assertions.assertFalse(validador.esValida("cheerleaders"));
    }

    @Test
    @DisplayName("Contraseña rechazada por no cumplir con la convención de caracteres")
    public void contraseniaNoCumpleConvencionDeCaracteres() throws IOException {
        Assertions.assertFalse(validador.esValida("ddsmanana123"));
    }
}
