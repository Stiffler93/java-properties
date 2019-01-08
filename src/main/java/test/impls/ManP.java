package test.impls;

import test.Man;

public final class ManP implements Man, Runnable {

    @Override
    public void work() {
        System.out.println("I am a Man and I work.");
    }

    @Override
    public void sleep() {
        System.out.println("I am a Man and sleep as well.");
    }

    @Override
    public void run() {

    }
}
