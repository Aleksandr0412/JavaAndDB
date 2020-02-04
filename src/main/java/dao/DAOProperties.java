package dao;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOProperties {
    private String url;
    private String user;
    private String password;
    private static DAOProperties instance;
    private static final String PATH_TO_PROPERTIES = "data.properties";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DAOProperties.class);

    private DAOProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES);
            properties.load(inputStream);
            this.url = properties.getProperty("jdbc.url");
            this.user = properties.getProperty("jdbc.user");
            this.password = properties.getProperty("jdbc.password");
            logger.info("read properties successful");
        } catch (IOException e) {
            logger.error("problem with reading properties" + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static DAOProperties getProperties() {
        if (instance == null) {
            instance = new DAOProperties();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
