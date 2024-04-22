package ar.edu.utn.frba.dds.validadorContrasenias;

import ar.edu.utn.frba.dds.utils.ValidadorDeContrasenias;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorDeContraseniasTest {

    @Test
    @DisplayName("Contraseña aceptada por cumplir con los requisitos")
    public void contraseniaAceptadaTest() throws ValidadorDeContrasenias.ContraseniaInvalidaException {
        Assertions.assertTrue(ValidadorDeContrasenias.esValida("DDS-Martes-Mañana-@2024"));

    }

    @Test
    @DisplayName("Contraseña rechazada por longitud muy corta")
    public void contraseniaNoCumpleConLongitudTest(){
        Assertions.assertThrows(ValidadorDeContrasenias.ContraseniaInvalidaException.class, () -> {
            ValidadorDeContrasenias.esValida("DDS");
        });
    }

    @Test
    @DisplayName("Contraseña rechazada ya que se encuentra dentro de las 10 mil más usadas")
    public void contraseniaEsInsegura(){
        Assertions.assertThrows(ValidadorDeContrasenias.ContraseniaInvalidaException.class, () -> {
            ValidadorDeContrasenias.esValida("cheerleaders");
        });
    }

    @Test
    @DisplayName("Contraseña rechazada por no cumplir con la convención de caracteres")
    public void contraseniaNoCumpleConvencionDeCaracteres(){
        Assertions.assertThrows(ValidadorDeContrasenias.ContraseniaInvalidaException.class, () -> {
            ValidadorDeContrasenias.esValida("ddsmanana123");
        });
    }
}
