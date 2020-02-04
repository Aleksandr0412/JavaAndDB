package dao;

import app.behavior.FlyBehavior;
import app.behavior.QuackBehavior;
import app.entities.Duck;
import app.entities.Frog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuckDAO extends AbstractDAO<Duck, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(DuckDAO.class);
    static final String SELECT_ALL_DUCKS = "SELECT * FROM DUCKS LEFT JOIN FROGS ON FROGS.ID = DUCKS.FROG_ID";
    static final String INSERT_DUCK = "INSERT INTO DUCKS VALUES (?, ?, ?, ?, ?);";
    static final String INSERT_FROG = "INSERT INTO FROGS VALUES (?, ?)";
    static final String DELETE_DUCK = "DELETE FROM DUCKS WHERE id = (?)";
    static final String DELETE_FROGS = "DELETE FROM FROGS WHERE id = (?)";
    static final String SELECT_DUCK_BY_ID = "SELECT * FROM DUCKS LEFT JOIN FROGS ON FROGS.ID = DUCKS.FROG_ID Where DUCKS.id = ?";
    static final String SELECT_FROG_FROM_DUCKS = "SELECT FROG_ID FROM DUCKS WHERE ID = ?";
    static final int ID = 1;
    static final int NAME = 2;
    static final int FLY_BEHAVIOR = 3;
    static final int QUACK_BEHAVIOR = 4;
    static final int ID_FROG = 5;
    static final int FROG_NAME = 7;

    public DuckDAO() {
    }

    ConnectionService getConnectionService() {
        return new ConnectionService();
    }

    @Override
    public List<Duck> getAll() {
        List<Duck> ducks = new ArrayList<>();
        ConnectionService connectionService = getConnectionService();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionService.getNewConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_DUCKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Duck duck = new Duck();
                Frog frog = new Frog();
                duck.id(resultSet.getInt(ID));
                duck.name(resultSet.getString(NAME));
                duck.setFlyBehavior(new FlyBehavior(resultSet.getString(FLY_BEHAVIOR)));
                duck.setQuackBehavior(new QuackBehavior(resultSet.getString(QUACK_BEHAVIOR)));
                frog.id(resultSet.getInt(ID_FROG));
                frog.name(resultSet.getString(FROG_NAME));
                duck.setMyFrogFriend(frog);
                ducks.add(duck);
            }
        } catch (SQLException e) {
            logger.error("problem with connection" + e.getMessage());
            return new ArrayList<>();
        } finally {
            connectionService.closeConnection(connection);
            connectionService.closeStatement(preparedStatement);
        }
        return ducks;
    }

    @Override
    public Duck getById(Integer id) {
        Duck duck = new Duck();
        Frog frog = new Frog();
        Connection connection = null;
        ConnectionService connectionService = getConnectionService();
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionService.getNewConnection();
            logger.info("connection successful");
            preparedStatement = connection.prepareStatement(SELECT_DUCK_BY_ID);
            preparedStatement.setInt(ID, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                duck.id(resultSet.getInt(ID));
                duck.name(resultSet.getString(NAME));
                duck.setFlyBehavior(new FlyBehavior(resultSet.getString(FLY_BEHAVIOR)));
                duck.setQuackBehavior(new QuackBehavior(resultSet.getString(QUACK_BEHAVIOR)));
                frog.id(resultSet.getInt(ID_FROG));
                frog.name(resultSet.getString(FROG_NAME));
                duck.setMyFrogFriend(frog);
            }
        } catch (SQLException e) {
            logger.error("problem with connection" + e.getMessage());
            return new Duck();
        } finally {
            connectionService.closeConnection(connection);
            connectionService.closeStatement(preparedStatement);
        }
        return duck;
    }

    @Override
    public void update(Duck entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConnectionService connectionService = getConnectionService();
        PreparedStatement duckPreparedStatement = null;
        try {
            connection = connectionService.getNewConnection();
            logger.info("connection successful");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_FROG);
            preparedStatement.setInt(ID, entity.getFrogId());
            preparedStatement.setString(NAME, entity.getMyFrogFriend().name());
            preparedStatement.executeUpdate();
            duckPreparedStatement = connection.prepareStatement(INSERT_DUCK);
            duckPreparedStatement.setInt(ID, entity.id());
            duckPreparedStatement.setString(NAME, entity.name());
            duckPreparedStatement.setString(FLY_BEHAVIOR, entity.getFlyBehavior().getFly());
            duckPreparedStatement.setString(QUACK_BEHAVIOR, entity.getQuackBehavior().getQuack());
            duckPreparedStatement.setInt(ID_FROG, entity.getFrogId());
            duckPreparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("error with statements or connection, can not make update" + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("transaction error " + e.getMessage());
            }
        } finally {
            connectionService.closeStatement(preparedStatement);
            connectionService.closeStatement(duckPreparedStatement);
            connectionService.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) {
        ConnectionService connectionService = getConnectionService();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement frogPreparedStatement = null;
        PreparedStatement duckPreparedStatement = null;
        int frogId = 0;
        try {
            connection = connectionService.getNewConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SELECT_FROG_FROM_DUCKS);
            preparedStatement.setInt(ID, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                frogId = resultSet.getInt(ID);
            }

            frogPreparedStatement = connection.prepareStatement(DELETE_FROGS);
            frogPreparedStatement.setInt(ID, frogId);
            frogPreparedStatement.execute();

            duckPreparedStatement = connection.prepareStatement(DELETE_DUCK);
            duckPreparedStatement.setInt(ID, id);
            duckPreparedStatement.execute();

            connection.commit();
        } catch (SQLException e) {
            logger.error("error with transaction " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Can not do rollback!!" + e.getMessage());
            }
        } finally {
            connectionService.closeStatement(preparedStatement);
            connectionService.closeStatement(frogPreparedStatement);
            connectionService.closeStatement(duckPreparedStatement);
            connectionService.closeConnection(connection);
        }
    }
}
