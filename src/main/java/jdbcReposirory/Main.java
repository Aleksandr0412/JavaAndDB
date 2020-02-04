package jdbcReposirory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JdbcConfig.class);
        DuckRep rep = ctx.getBean(DuckRep.class);
        System.out.println(rep.getAll());
    }
}
