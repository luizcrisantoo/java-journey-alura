package com.luizcrisanto.consultaapi.model;

import java.util.OptionalDouble;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

     public static Categoria fromPortugues(String texto) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.name().equalsIgnoreCase(texto)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria nao encontrada");
    }
}
    

