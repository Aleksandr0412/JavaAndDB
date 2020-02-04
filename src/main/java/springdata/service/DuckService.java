package springdata.service;

import springdata.entities.DuckJpa;

import java.util.List;
import java.util.Optional;

public interface DuckService {
    List<DuckJpa> findAll();

    Optional<DuckJpa> findById(Integer i);

    DuckJpa save(DuckJpa duckJpa);

    void delete(DuckJpa duckJpa);

    void deleteById(Integer id);
}
