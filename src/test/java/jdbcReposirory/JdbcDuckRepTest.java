package jdbcReposirory;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JdbcDuckRepTest {
    private JdbcTemplate jdbcTemplateMock;
    private JdbcDuckRep jdbcDuckRep;

    @BeforeTest
    public void init() {
        jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        jdbcDuckRep = Mockito.spy(JdbcDuckRep.class);
        jdbcDuckRep.setJdbcTemplate(jdbcTemplateMock);
    }

    @Test
    public void testDeleteAll() {
        jdbcDuckRep.deleteAll();

        Mockito.verify(jdbcTemplateMock, Mockito.times(1)).execute(JdbcDuckRep.DELETE_ALL_DUCKS);
        Mockito.verify(jdbcTemplateMock, Mockito.times(1)).execute(JdbcDuckRep.DELETE_ALL_FROGS);
    }

    @Test
    public void testGetAll() {
        ArgumentCaptor<JdbcDuckRep.DuckMapper> acDuckMapper = ArgumentCaptor.forClass(JdbcDuckRep.DuckMapper.class);

        jdbcDuckRep.getAll();

        Mockito.verify(jdbcTemplateMock, Mockito.times(1)).query(Mockito.anyString(), acDuckMapper.capture());
    }

    @Test
    public void testGetByName() {
        jdbcDuckRep.getByName(Mockito.anyString());
        ArgumentCaptor<JdbcDuckRep.DuckMapper> acDuckMapper = ArgumentCaptor.forClass(JdbcDuckRep.DuckMapper.class);

        Mockito.verify(jdbcTemplateMock, Mockito.times(1)).query(Mockito.anyString(), (Object[]) Mockito.any(), acDuckMapper.capture());
    }
}