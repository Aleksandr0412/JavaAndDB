package mvc.controller;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import springdata.entities.DuckJpa;
import springdata.service.DuckService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class GreetingControllerTest {
    @Mock
    private DuckService duckService;

    private int mapSize;
    private Map<String, Object> model;
    private List<DuckJpa> expectedList;
    private String name = "qwe";
    private static final int id = 3;
    private static final String NAME = "name";
    private static final String BEHAVIOR = "fly";
    private static final String BEHAVIOR2 = "quack";

    @BeforeMethod
    public void init() {
        initMocks(this);
        mapSize = new Random().nextInt(10) + 1;
        model = new HashMap<>(mapSize);
        for (int i = 0; i < mapSize; i++) {
            model.put(UUID.randomUUID().toString(), mock(Object.class));
        }

        expectedList = new ArrayList<>();
    }

    @Test
    public void testMainMethod() {
        when(duckService.findAll()).thenReturn(expectedList);

        final int expectedMapSize = model.size();

        assertThat(model).doesNotContainKey("ducks");

        final String actual = new GreetingController(duckService).main(model);
        assertThat(actual).isEqualTo("main");

        assertThat(model).hasSize(expectedMapSize + 1);
        assertThat(model).containsKey("ducks");
        assertThat(model.get("ducks")).isSameAs(expectedList);

        final InOrder inOrder = Mockito.inOrder(duckService);
        inOrder.verify(duckService).findAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testGreeting() throws Exception {
        assertThat(model).doesNotContainKey("name");

        final String actual = new GreetingController(duckService).greeting(name, model);

        assertThat(actual).isEqualTo("greeting");
        assertThat(model).containsKey("name");

    }

    @Test
    public void testAdd() {
        final String actual = new GreetingController(duckService).add(id, NAME, BEHAVIOR, BEHAVIOR2, id, NAME, model);

        verify(duckService).save(any());
        verify(duckService).findAll();
        assertThat(actual).isEqualTo("main");
    }

    @Test
    public void testDelete() {
        final String actual = new GreetingController(duckService).delete(id, model);

        assertThat(actual).isEqualTo("main");
        verify(duckService).deleteById(id);
        verify(duckService).findAll();
    }

    @Test
    public void testFindById() {
        DuckJpa duckJpa = new DuckJpa();
        Optional<DuckJpa> opt = Optional.of(duckJpa);

        when(duckService.findById(id)).thenReturn(opt);
        final String actual = new GreetingController(duckService).findById(id, model);

        verify(duckService).findById(id);
        assertThat(actual).isEqualTo("main");
    }
}