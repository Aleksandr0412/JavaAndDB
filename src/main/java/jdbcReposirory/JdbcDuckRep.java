package jdbcReposirory;

import app.behavior.FlyBehavior;
import app.behavior.QuackBehavior;
import app.entities.Duck;
import app.entities.Frog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcDuckRep implements DuckRep {

    private JdbcTemplate jdbcTemplate;
    static final int ID = 1;
    static final int NAME = 2;
    static final int FLY_BEHAVIOR = 3;
    static final int QUACK_BEHAVIOR = 4;
    static final int ID_FROG = 5;
    static final int FROG_NAME = 7;

    static final String DELETE_ALL_DUCKS = "Delete From Ducks";
    static final String DELETE_ALL_FROGS = "Delete From Frogs";
    static final String SELECT_ALL = "SELECT * FROM DUCKS LEFT JOIN FROGS ON FROGS.ID = DUCKS.FROG_ID";
    static final String SELECT_BY_NAME = "SELECT * FROM DUCKS LEFT JOIN FROGS ON FROGS.ID = DUCKS.FROG_ID Where DUCKS.NAME = ?";

    void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static final class DuckMapper implements RowMapper<Duck> {
        @Override
        public Duck mapRow(ResultSet resultSet, int i) throws SQLException {
            Duck duck = new Duck();
            Frog frog = new Frog();
            duck.id(resultSet.getInt(ID));
            duck.name(resultSet.getString(NAME));
            duck.setFlyBehavior(new FlyBehavior(resultSet.getString(FLY_BEHAVIOR)));
            duck.setQuackBehavior(new QuackBehavior(resultSet.getString(QUACK_BEHAVIOR)));
            frog.id(resultSet.getInt(ID_FROG));
            frog.name(resultSet.getString(FROG_NAME));
            duck.setMyFrogFriend(frog);

            return duck;
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        jdbcTemplate.execute(DELETE_ALL_DUCKS);
        jdbcTemplate.execute(DELETE_ALL_FROGS);
    }

    @Override
    public List<Duck> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new DuckMapper());
    }

    @Override
    public List<Duck> getByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME, new Object[]{name}, new DuckMapper());
    }
}
