package jdbcReposirory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:data.properties")
public class JdbcConfig {

    private String url;
    private String user;
    private String password;

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(env.getProperty("jdbc.url"));
        basicDataSource.setUsername(env.getProperty("jdbc.user"));
        basicDataSource.setPassword(env.getProperty("jdbc.password"));

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    DuckRep duckRep(final JdbcTemplate jdbcTemplate) {
        final JdbcDuckRep jdbcDuckRep = new JdbcDuckRep();
        jdbcDuckRep.setJdbcTemplate(jdbcTemplate);

        return jdbcDuckRep;
    }
}