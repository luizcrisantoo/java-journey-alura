package com.luizcrisanto.consultaapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.luizcrisanto.consultaapi.model.Serie;


public interface SerieRepository extends JpaRepository<Serie, Long> {
}