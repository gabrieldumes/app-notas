package com.gabriel.notas12;

import java.io.Serializable;

public class Nota implements Serializable {

    private String titulo, nota;
    private boolean lembreteAtivado;
    private int id;

    public Nota(String titulo, String nota, boolean lembreteAtivado, int id) {
        this.titulo = titulo;
        this.nota = nota;
        this.lembreteAtivado = lembreteAtivado;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public boolean isLembreteAtivado() {
        return lembreteAtivado;
    }

    public void setLembreteAtivado(boolean lembreteAtivado) {
        this.lembreteAtivado = lembreteAtivado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
