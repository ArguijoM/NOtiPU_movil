package com.example.notipush;

public class Notificacion {
    private String asunto;
    private String descripcion;
    private String grupo;

    public Notificacion(String asunto, String descripcion, String grupo) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.grupo = grupo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
