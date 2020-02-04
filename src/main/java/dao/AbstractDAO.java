package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractDAO<E, K> {
    private static final Logger log = Logger.getLogger(DuckDAO.class.getName());

    private static final String PATH_TO_PROPERTIES = "data.properties";

    public abstract List<E> getAll() throws SQLException;

    public abstract E getById(K id) throws SQLException;

    public abstract void update(E entity) throws SQLException;

    public abstract void delete(K id) throws SQLException;
}
