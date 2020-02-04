package mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springdata.entities.DuckJpa;
import springdata.entities.FrogJpa;
import springdata.service.DuckService;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class GreetingController {

    private final DuckService duckService;

    @Autowired
    public GreetingController(final DuckService duckService) {
        this.duckService = duckService;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);

        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        final Iterable<DuckJpa> all = duckService.findAll();
        model.put("ducks", all);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam Integer id, @RequestParam String name, @RequestParam String flyBehavior,
                      @RequestParam String quackBehavior, @RequestParam Integer frog_id,
                      @RequestParam String frogName, Map<String, Object> model) {
        DuckJpa duckJpa = new DuckJpa(id, name, flyBehavior, quackBehavior);
        FrogJpa frogJpa = new FrogJpa(frog_id, frogName);
        duckJpa.setFrogJpa(frogJpa);
        duckService.save(duckJpa);

        Iterable<DuckJpa> all = duckService.findAll();
        model.put("ducks", all);

        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam Integer id, Map<String, Object> model) {
        duckService.deleteById(id);

        Iterable<DuckJpa> all = duckService.findAll();
        model.put("ducks", all);

        return "main";
    }

    @PostMapping("find")
    public String findById(@RequestParam Integer id, Map<String, Object> model) {
        ArrayList<DuckJpa> list = new ArrayList<>();
        list.add(duckService.findById(id).get());

        model.put("ducks", list);

        return "main";
    }
}
