package com.luizcrisanto.consultaapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.luizcrisanto.consultaapi.service.*;

@SpringBootApplication
public class ConsultaApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaApiApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
        System.out.println(json);
	}
}
