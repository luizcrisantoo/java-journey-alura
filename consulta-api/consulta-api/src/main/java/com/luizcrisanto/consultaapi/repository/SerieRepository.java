package com.luizcrisanto.consultaapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.luizcrisanto.consultaapi.model.Categoria;
import com.luizcrisanto.consultaapi.model.Serie;
import java.util.List;
import java.util.Optional;



public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional <Serie>findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);
}