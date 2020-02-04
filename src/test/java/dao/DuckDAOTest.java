package dao;

import app.behavior.FlyBehavior;
import app.behavior.QuackBehavior;
import app.entities.Duck;
import app.entities.Frog;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DuckDAOTest {
    private static final int ID = (int) (Math.random() * 10) + 1;
    private static final String NAME = "JACK";
    private static final String FROG_NAME = "JABA";
    private static final String FLY = "FLY";
    private static final String QUACK = "QUACK";
    private Duck duck;

    @BeforeTest
    public void init() {
        duck = new Duck();
        duck.id(ID);
        duck.name(NAME);
        duck.setFlyBehavior(new FlyBehavior(FLY));
        duck.setQuackBehavior(new QuackBehavior(QUACK));

        Frog frog = new Frog();
        frog.id(ID);
        frog.name(FROG_NAME);
        duck.setMyFrogFriend(frog);
    }

    @Test
    public void testGetAll() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        DuckDAO duckDAO = Mockito.spy(DuckDAO.class);
        ConnectionService connectionServiceMock = Mockito.mock(ConnectionService.class);
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        Mockito.when(duckDAO.getConnectionService()).thenReturn(connectionServiceMock);
        Mockito.when(connectionServiceMock.getNewConnection()).thenReturn(connectionMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.SELECT_ALL_DUCKS)).thenReturn(preparedStatementMock);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true, false);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.getInt(DuckDAO.ID)).thenReturn(ID);
        Mockito.when(resultSetMock.getString(DuckDAO.NAME)).thenReturn("JACK");
        Mockito.when(resultSetMock.getString(DuckDAO.FLY_BEHAVIOR)).thenReturn("FLY");
        Mockito.when(resultSetMock.getString(DuckDAO.QUACK_BEHAVIOR)).thenReturn("QUACK");
        Mockito.when(resultSetMock.getInt(DuckDAO.ID_FROG)).thenReturn(ID);
        Mockito.when(resultSetMock.getString(DuckDAO.FROG_NAME)).thenReturn("JABA");

        Assert.assertEquals(duckDAO.getAll().get(0).name(), duck.name());

        Mockito.verify(resultSetMock, Mockito.times(1)).getInt(DuckDAO.ID);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.NAME);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.FLY_BEHAVIOR);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.QUACK_BEHAVIOR);
        Mockito.verify(resultSetMock, Mockito.times(1)).getInt(DuckDAO.ID_FROG);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.FROG_NAME);
    }

    @Test
    public void testGetById() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        DuckDAO duckDAO = Mockito.spy(DuckDAO.class);
        ConnectionService connectionServiceMock = Mockito.mock(ConnectionService.class);
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        Mockito.when(duckDAO.getConnectionService()).thenReturn(connectionServiceMock);
        Mockito.when(connectionServiceMock.getNewConnection()).thenReturn(connectionMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.SELECT_DUCK_BY_ID)).thenReturn(preparedStatementMock);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true, false);

        duckDAO.getById(ID);

        Mockito.verify(resultSetMock, Mockito.times(1)).getInt(DuckDAO.ID);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.NAME);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.FLY_BEHAVIOR);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.QUACK_BEHAVIOR);
        Mockito.verify(resultSetMock, Mockito.times(1)).getInt(DuckDAO.ID_FROG);
        Mockito.verify(resultSetMock, Mockito.times(1)).getString(DuckDAO.FROG_NAME);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeStatement(preparedStatementMock);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeConnection(connectionMock);
    }

    @Test
    public void testUpdate() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        DuckDAO duckDAO = Mockito.spy(DuckDAO.class);
        PreparedStatement preparedStatementMock1 = Mockito.mock(PreparedStatement.class);
        ConnectionService connectionServiceMock = Mockito.mock(ConnectionService.class);

        Mockito.when(duckDAO.getConnectionService()).thenReturn(connectionServiceMock);
        Mockito.when(connectionServiceMock.getNewConnection()).thenReturn(connectionMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.INSERT_FROG)).thenReturn(preparedStatementMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.INSERT_DUCK)).thenReturn(preparedStatementMock1);

        duckDAO.update(duck);

        Mockito.verify(connectionServiceMock, Mockito.times(1)).getNewConnection();
        Mockito.verify(connectionMock, Mockito.times(1)).setAutoCommit(false);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).setInt(DuckDAO.ID, duck.getFrogId());
        Mockito.verify(preparedStatementMock, Mockito.times(1)).setString(DuckDAO.NAME, duck.getMyFrogFriend().name());
        Mockito.verify(preparedStatementMock, Mockito.times(1)).executeUpdate();

        Mockito.verify(preparedStatementMock1, Mockito.times(1)).setInt(DuckDAO.ID, duck.id());
        Mockito.verify(preparedStatementMock1, Mockito.times(1)).setString(DuckDAO.NAME, duck.name());
        Mockito.verify(preparedStatementMock1, Mockito.times(1)).setString(DuckDAO.FLY_BEHAVIOR, duck.getFlyBehavior().getFly());
        Mockito.verify(preparedStatementMock1, Mockito.times(1)).setString(DuckDAO.QUACK_BEHAVIOR, duck.getQuackBehavior().getQuack());
        Mockito.verify(preparedStatementMock1, Mockito.times(1)).setInt(DuckDAO.ID_FROG, duck.getFrogId());
        Mockito.verify(preparedStatementMock1, Mockito.times(1)).executeUpdate();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeStatement(preparedStatementMock1);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeConnection(connectionMock);
    }

    @Test
    public void testDelete() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        PreparedStatement frogPreparedStatementMock = Mockito.mock(PreparedStatement.class);
        PreparedStatement duckPreparedStatementMock = Mockito.mock(PreparedStatement.class);
        ConnectionService connectionServiceMock = Mockito.mock(ConnectionService.class);
        DuckDAO duckDAO = Mockito.spy(DuckDAO.class);
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        Mockito.when(duckDAO.getConnectionService()).thenReturn(connectionServiceMock);
        Mockito.when(connectionServiceMock.getNewConnection()).thenReturn(connectionMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.SELECT_FROG_FROM_DUCKS)).thenReturn(preparedStatementMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.DELETE_FROGS)).thenReturn(frogPreparedStatementMock);
        Mockito.when(connectionMock.prepareStatement(DuckDAO.DELETE_DUCK)).thenReturn(duckPreparedStatementMock);
        Mockito.when(resultSetMock.next()).thenReturn(true, false);
        Mockito.when(resultSetMock.getInt(DuckDAO.ID)).thenReturn(ID);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

        duckDAO.delete(ID);

        Mockito.verify(connectionMock, Mockito.times(1)).setAutoCommit(false);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).setInt(1, ID);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).executeQuery();
        Mockito.verify(frogPreparedStatementMock, Mockito.times(1)).setInt(DuckDAO.ID, ID);
        Mockito.verify(frogPreparedStatementMock, Mockito.times(1)).execute();
        Mockito.verify(duckPreparedStatementMock, Mockito.times(1)).setInt(DuckDAO.ID, ID);
        Mockito.verify(duckPreparedStatementMock, Mockito.times(1)).execute();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeConnection(connectionMock);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeStatement(preparedStatementMock);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeStatement(frogPreparedStatementMock);
        Mockito.verify(connectionServiceMock, Mockito.times(1)).closeStatement(duckPreparedStatementMock);
    }
}