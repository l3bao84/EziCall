package ATP.Project.EziCall.models;

public class Ent<T> {

    private T t;

    public Ent(T t) {
        this.t = t;
    }

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

}
