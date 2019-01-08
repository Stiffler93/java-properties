package test;

import sonntag.properties.ObjectP;
import test.impls.ManP;

public class Main {

    public static void main(String[] args) {
        ObjectP o1 = ObjectP.create();
        o1.addProperty(Name.class, () -> "Hans");
        o1.addProperty(Man.class, new ManP());
        canBuildHouse(o1);

        System.out.println("Now check reflective creation: ");

        Person o2 = ObjectP.create(Person.class);

        if(o2.is(Human.class)) {
            System.out.println("Person successfully created with Human Property!");
        }

        if(o2.is(Man.class)) {
            System.out.println("Person is also a Man!");
        }

        canBuildHouse(o2);
    }

    private static void canBuildHouse(ObjectP objectP) {
        if(objectP.is(Name.class)) {
            Name name = objectP.as(Name.class);
            System.out.println(String.format("Let's check if %s can build a house", name.getName()));
        } else {
            System.out.println("Let's check if the guy without a name can build a house");
        }

        if(objectP.is(Man.class)) {
            System.out.println("A man can build a house");
            Man man = objectP.as(Man.class);
            man.work();
            man.sleep();
        }

        System.out.println("Whatever you believe... this guy definitely cannot build a house!");
    }
}
