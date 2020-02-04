package springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.entities.DuckJpa;

@Repository("duckJpaRepository")
public interface DuckJpaRepository extends JpaRepository<DuckJpa, Integer> {
    void deleteById(Integer id);
}

