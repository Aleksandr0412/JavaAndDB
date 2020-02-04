package app.entities;

import app.behavior.FlyBehavior;
import app.behavior.QuackBehavior;

public class Duck {
    private int id;
    private String name;
    private Frog myFrogFriend;
    private FlyBehavior flyBehavior;
    private QuackBehavior quackBehavior;

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public Duck() {
    }

    public Duck(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getFrogId() {
        return myFrogFriend.id();
    }

    public Frog getMyFrogFriend() {
        return myFrogFriend;
    }

    public void setMyFrogFriend(Frog frog) {
        this.myFrogFriend = frog;
    }

    public void id(final int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void name(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Duck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", myFrogFriend=" + myFrogFriend +
                '}';
    }
}
