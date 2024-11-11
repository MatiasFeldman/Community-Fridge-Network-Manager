package ar.edu.utn.frba.dds.dtos;

public class ContactosDTO {
    private String tipoContacto; // Ej: "Mail", "WhatsApp", "Telegram"
    private String valor;        // Valor del contacto, Ej: "ejemplo@mail.com"

    // Constructor
    public ContactosDTO(String tipoContacto, String valor) {
        this.tipoContacto = tipoContacto;
        this.valor = valor;
    }

    // Getters y Setters
    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}