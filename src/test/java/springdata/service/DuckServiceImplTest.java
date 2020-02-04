package springdata.service;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import springdata.entities.DuckJpa;
import springdata.repository.DuckJpaRepository;

import java.util.ArrayList;

public class DuckServiceImplTest {
    DuckJpaRepository duckJpaRepository;
    DuckServiceImpl duckService;
    DuckJpa duckJpa;

    @BeforeTest
    public void init() {
        duckJpaRepository = Mockito.mock(DuckJpaRepository.class);
        duckService = Mockito.spy(DuckServiceImpl.class);
        duckService.setDuckJpaRepository(duckJpaRepository);
        duckJpa = Mockito.mock(DuckJpa.class);
    }

    @Test
    public void testFindAll() {
        ArrayList list = new ArrayList();
        Mockito.when(duckJpaRepository.findAll()).thenReturn(list);

        Assert.assertEquals(duckService.findAll(), list);
        Mockito.verify(duckJpaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindById() {
        duckService.findById(1);

        Mockito.verify(duckJpaRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testSave() {
        duckService.save(duckJpa);

        Mockito.verify(duckJpaRepository, Mockito.times(1)).save(duckJpa);
    }

    @Test
    public void testDelete() {
        duckService.delete(duckJpa);

        Mockito.verify(duckJpaRepository, Mockito.times(1)).delete(duckJpa);
    }
}