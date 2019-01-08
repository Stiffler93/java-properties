package test;

public final class EngineerP implements Engineer {

    @Override
    public void think() {
        System.out.println("I am an Engineer and I think.");
    }

    @Override
    public void work() {
        System.out.println("I am an Engineer and I work.");
    }
}
