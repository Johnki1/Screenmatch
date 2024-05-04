package com.aluracursos.screenmatch.principal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("APIOMDB_KEY");
    private ConvierteDatos conversor = new ConvierteDatos();
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void mostrarElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1. Buscar Series
                    2. Buscar Episodios
                    3. Mostrar Series Buscadas
                    4. Buscar Series Por Titulo
                    5. Top 5 Mejores Series
                    6. Buscar Serie Por Categoria
                    7. Buscar Serie Por Total Temporadas Y Evaluacion
                    8. Buscar Episodios Por Nombre
                    9. Top 5 Episodios Por Serie
                    
                    0. Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    buscarSeriePorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    top5Episodios();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("Opcion Invalida");
            }
        }
    }

    private DatosSerie getDatosSerie(){
        //Busca datos generales de las series
        System.out.println("Por favor ingrese el nombre de la serie que desea buscar");
        var nombreSerie= sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+ nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
        var nombreSerie = sc.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s-> s.getTitulo().toUpperCase().contains(nombreSerie.toUpperCase()))
                .findFirst();
        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i < serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY);
                DatosTemporadas datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        try{
            repositorio.save(serie);
        }catch (DataIntegrityViolationException e){
            System.out.println("Error: La serie ya existe en la base de datos");
        }
        System.out.println(datos);

    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo(){
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = sc.nextLine();
        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()){
            System.out.println("Serie buscada: "+ serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }

    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(e -> System.out.println("Serie: "+ e.getTitulo() + " Evaluacion: "+ e.getEvaluacion()));
    }

    private void buscarSeriePorCategoria(){
        System.out.println("Escribe el genero/categoria de la serie que deseas buscar");
        var genero = sc.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de la categoria: "+ genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriePorTemporadasYEvaluacion(){
        System.out.println("Escribe el numero de temporadas");
        var totalTeporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("Escribe la evaluacion");
        var evaluacion = sc.nextDouble();
        sc.nextLine();
        List<Serie> seriePorTemporadasYEvaluacion= repositorio.seriesPorTemporadaYEvaluacion(totalTeporadas, evaluacion);
        System.out.println("*+* Series Filtradas *+*");
        seriePorTemporadasYEvaluacion.forEach(e ->
                        System.out.println(e.getTitulo() + " - Evaluacion: " + e.getEvaluacion())
                );
    }

    private void buscarEpisodiosPorTitulo(){
        System.out.println("Ingrese el nombre del episodio que desea buscar");
        var nombreEpisodio = sc.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodioPorNombre(nombreEpisodio);
         episodiosEncontrados.forEach(e ->
                         System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n",
                                 e.getSerie(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion())
                 );
    }

    private void top5Episodios(){
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5MejoresEpisodios(serie);
            topEpisodios.forEach(e ->
                            System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion: %s\n",
                                    e.getSerie().getTitulo(),e.getTemporada() ,e.getTitulo(), e.getEvaluacion())
                    );
        }

    }

}
