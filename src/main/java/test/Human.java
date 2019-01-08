package test;

import sonntag.properties.Property;

public interface Human extends Property {

    default void whatAmI() {
        System.out.println("I am a Human!");
    }
}
