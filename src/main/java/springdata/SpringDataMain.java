package springdata;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springdata.service.DuckService;
import springdata.service.DuckServiceImpl;

public class SpringDataMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringDataConfig.class);
        DuckService service = ctx.getBean(DuckService.class);
        System.out.println(service.findAll());
    }
}
