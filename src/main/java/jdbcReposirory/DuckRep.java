package jdbcReposirory;

import app.entities.Duck;

import java.util.List;

public interface DuckRep {
    void deleteAll();
    List<Duck> getAll();
    List<Duck> getByName(String name);
}
