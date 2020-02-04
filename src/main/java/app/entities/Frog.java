package app.entities;

import java.util.Objects;

public class Frog {
    private int id;
    private String name;

    public String name() {
        return this.name;
    }

    public void name(String name) {
        this.name = name;
    }

    public int id() {
        return this.id;
    }

    public void id(int id) {
        this.id = id;
    }

    public Frog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Frog() {
    }
}
