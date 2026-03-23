package com.luizcrisanto.consultaapi.principal;

import com.luizcrisanto.consultaapi.model.Categoria;
import com.luizcrisanto.consultaapi.model.DadosEpisodio;
import com.luizcrisanto.consultaapi.model.DadosSerie;
import com.luizcrisanto.consultaapi.model.DadosTemporada;
import com.luizcrisanto.consultaapi.model.Episodio;
import com.luizcrisanto.consultaapi.model.Serie;
import com.luizcrisanto.consultaapi.repository.SerieRepository;
import com.luizcrisanto.consultaapi.service.ConsumoApi;
import com.luizcrisanto.consultaapi.service.ConverteDados;

import java.util.*;
import java.util.stream.*;


public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar séries por título
                    5 - Buscar séries por ator
                    6 - Buscar Top 5 séries
                    7 - Buscar séries por categoria
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serieBuscada =
                repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            exibirSerieFormatada(serieBuscada.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }
    
    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repositorio.save(serie);
        exibirDadosSerie(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série para buscar pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional <Serie> serie = series.stream().filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase())).findFirst();

        if (serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                    var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                    temporadas.add(dadosTemporada);
                }

            exibirTemporadas(temporadas);

            List<Episodio> episodios = temporadas.stream()
                .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(), e)))
                .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
        System.out.println("Série não encontrada");
        }
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();

        System.out.println("\n========================================");
        System.out.println("Séries buscadas");
        System.out.println("========================================");

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(this::exibirSerieFormatada);

        System.out.println();
    }

    private void exibirDadosSerie(DadosSerie dados) {
        System.out.println("\n========================================");
        System.out.println("Série encontrada");
        System.out.println("========================================");
        System.out.println("Título: " + dados.titulo());
        System.out.println("Avaliação: " + dados.avaliacao());
        System.out.println("Ano: " + dados.ano());
        System.out.println("Total de temporadas: " + dados.totalTemporadas());
        System.out.println("Gênero: " + dados.genero());
        System.out.println("Atores: " + dados.atores());
        System.out.println("Sinopse: " + dados.sinopse());
        System.out.println("========================================\n");
    }

    private void exibirTemporadas(List<DadosTemporada> temporadas) {
        System.out.println("\n========================================");
        System.out.println("Episódios por temporada");
        System.out.println("========================================");

        for (DadosTemporada temporada : temporadas) {
            System.out.println("\n TEMPORADA " + temporada.numero());
            System.out.println("----------------------------------------");

            for (DadosEpisodio episodio : temporada.episodios()) {
                System.out.println(episodio.titulo());
                System.out.println("Episódio: " + episodio.numero());
                System.out.println("Avaliação: " + episodio.avaliacao());
                System.out.println("Lançamento: " + episodio.dataLancamento());
                System.out.println("----------------------------------------");
            }
        }

        System.out.println();
    }

    private void exibirSerieFormatada(Serie serie) {
        System.out.println("Título: " + serie.getTitulo());
        System.out.println("Avaliação: " + serie.getAvaliacao());
        System.out.println("Temporadas: " + serie.getTotalTemporadas());
        System.out.println("Gênero: " + serie.getGenero());
        System.out.println("Atores: " + serie.getAtores());
        System.out.println("Sinopse: " +serie.getSinopse());
        System.out.println("----------------------------------------");
    }

    private void buscarSeriesPorAtor() {
        System.out.println(" Qual o nome do ator para busca?");
        var nomeAtor = leitura.nextLine();

        System.out.println(" Avaliações a partir de que valor?");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine(); // importante!

        List<Serie> seriesEncontradas =
                repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(
                        nomeAtor,
                        avaliacao
                );

        if (seriesEncontradas.isEmpty()) {
            System.out.println("\n Nenhuma série encontrada para esse ator.\n");
            return;
        }

        System.out.println("\n========================================");
        System.out.println(" Séries em que " + nomeAtor + " participou:");
        System.out.println("========================================\n");

        seriesEncontradas.forEach(this::exibirSerieFormatada);
    }

    private void buscarTop5Series() {

        List<Serie> serieTop =
                repositorio.findTop5ByOrderByAvaliacaoDesc();

        if (serieTop.isEmpty()) {
            System.out.println("\nNenhuma série encontrada.\n");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("TOP 5 SERIES MAIS BEM AVALIADAS");
        System.out.println("========================================\n");

        serieTop.forEach(this::exibirSerieResumo);
    }

    private void exibirSerieResumo(Serie serie) {
        System.out.println(
                "Titulo: " + serie.getTitulo()
                + " | Avaliacao: " + serie.getAvaliacao()
                + " | Temporadas: " + serie.getTotalTemporadas()
        );
    }

    private void buscarSeriesPorCategoria() {

        System.out.println("Deseja buscar series de que categoria/genero?");
        var nomeGenero = leitura.nextLine();

        try {

            Categoria categoria =
                    Categoria.fromPortugues(nomeGenero);

            List<Serie> seriesPorCategoria =
                    repositorio.findByGenero(categoria);

            if (seriesPorCategoria.isEmpty()) {
                System.out.println("\nNenhuma serie encontrada para essa categoria.\n");
                return;
            }

            System.out.println("\n========================================");
            System.out.println("SERIES DA CATEGORIA: " + nomeGenero.toUpperCase());
            System.out.println("========================================\n");

            seriesPorCategoria.forEach(this::exibirSerieResumo);

        } catch (IllegalArgumentException e) {

            System.out.println("\nCategoria invalida.");
            System.out.println("Categorias disponiveis:");

            for (Categoria c : Categoria.values()) {
                System.out.println("- " + c);
            }

            System.out.println();
        }
    }

}