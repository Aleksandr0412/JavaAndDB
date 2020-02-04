package springdata.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FROGS")
public class FrogJpa {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrogJpa)) return false;
        FrogJpa frogJpa = (FrogJpa) o;
        return id == frogJpa.id &&
                Objects.equals(duckJpa, frogJpa.duckJpa) &&
                Objects.equals(name, frogJpa.name);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(duckJpa, id, name);
//    }

    public FrogJpa() {
    }

    public FrogJpa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToOne(mappedBy = "frogJpa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DuckJpa duckJpa;

    @Id
    private int id;
    @Column(name = "name")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuckJpa(DuckJpa duckJpa) {
        this.duckJpa = duckJpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DuckJpa getDuckJpa() {
        return duckJpa;
    }
}
