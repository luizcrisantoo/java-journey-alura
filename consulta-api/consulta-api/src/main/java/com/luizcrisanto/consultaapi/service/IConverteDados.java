package com.luizcrisanto.consultaapi.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}