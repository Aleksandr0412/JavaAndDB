package springdata.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "DUCKS")
public class DuckJpa implements Serializable {
    @Id
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FLY")
    private String flyBehavior;
    @Column(name = "QUACK")
    private String quackBehavior;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FROG_ID", unique = true, nullable = false, updatable = false)
    private FrogJpa frogJpa;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlyBehavior(String flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(String quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void setFrogJpa(FrogJpa frogJpa) {
        this.frogJpa = frogJpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlyBehavior() {
        return flyBehavior;
    }

    public String getQuackBehavior() {
        return quackBehavior;
    }

    public FrogJpa getFrogJpa() {
        return frogJpa;
    }

    public DuckJpa() {
    }

    public DuckJpa(int id, String name, String flyBehavior, String quackBehavior) {
        this.id = id;
        this.name = name;
        this.flyBehavior = flyBehavior;
        this.quackBehavior = quackBehavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuckJpa)) return false;
        DuckJpa duckJpa = (DuckJpa) o;
        return id == duckJpa.id &&
                Objects.equals(name, duckJpa.name) &&
                Objects.equals(flyBehavior, duckJpa.flyBehavior) &&
                Objects.equals(quackBehavior, duckJpa.quackBehavior) &&
                Objects.equals(frogJpa, duckJpa.frogJpa);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, flyBehavior, quackBehavior, frogJpa);
//    }
}
