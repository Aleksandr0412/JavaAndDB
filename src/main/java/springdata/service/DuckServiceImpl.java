package springdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.entities.DuckJpa;
import springdata.repository.DuckJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("springJpaDuckService")
@Transactional
public class DuckServiceImpl implements DuckService {
    private static final Logger logger = LoggerFactory.getLogger(DuckServiceImpl.class);


    @Autowired
    @Qualifier("duckJpaRepository")
    private DuckJpaRepository duckJpaRepository;

    @Transactional(readOnly = true)
    public List<DuckJpa> findAll() {
        return new ArrayList<>(duckJpaRepository.findAll());
    }

    public void setDuckJpaRepository(DuckJpaRepository duckJpaRepository) {
        this.duckJpaRepository = duckJpaRepository;
    }

    @Transactional
    @Override
    public Optional<DuckJpa> findById(Integer i) {
        return duckJpaRepository.findById(i);
    }

    @Transactional
    @Override
    public DuckJpa save(DuckJpa duckJpa) {
        DuckJpa duckJpa1 = duckJpaRepository.save(duckJpa);
        logger.info("Save successful");
        return duckJpa1;
    }

    @Transactional
    @Override
    public void delete(DuckJpa duckJpa) {
        duckJpaRepository.delete(duckJpa);
        logger.info("delete successful");
    }

    @Override
    public void deleteById(Integer id) {
        duckJpaRepository.deleteById(id);
        logger.info("delete successful");
    }
}