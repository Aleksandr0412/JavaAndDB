package dao;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionService {
    private DAOProperties properties = DAOProperties.getProperties();
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectionService.class);

    Connection getNewConnection() {
        try {
            return DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            logger.error("error with new connection" + e.getMessage());
            throw new RuntimeException();
        }
    }

    void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("can't close connection" + e.getMessage());
            throw new RuntimeException();
        }
    }

    void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.error("can't close statement" + e.getMessage());
                throw new RuntimeException();
            }
        }
    }
}


