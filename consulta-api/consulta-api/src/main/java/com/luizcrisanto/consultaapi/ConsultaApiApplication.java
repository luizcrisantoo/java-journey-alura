package com.luizcrisanto.consultaapi;

import com.luizcrisanto.consultaapi.principal.Principal;
import com.luizcrisanto.consultaapi.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaApiApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ConsultaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorio);
        principal.exibeMenu();
    }
}