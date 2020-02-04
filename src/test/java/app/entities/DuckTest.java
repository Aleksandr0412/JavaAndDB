package app.entities;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DuckTest {
    @Test
    public void testDuckShouldHaveFrog () {
        Duck duck = new Duck(1,"utya");
        Frog frog = new Frog(1, "jaba");
        duck.setMyFrogFriend(frog);
        Assert.assertEquals(frog.id(), duck.getFrogId());
        Assert.assertEquals(duck.getMyFrogFriend(), frog);
    }

}