package com.example.notipu;

public class Notificacion {
    private String asunto;
    private String descripcion;
    private int grupo;

    public Notificacion(String asunto, String descripcion, int grupo) {
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

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
}
