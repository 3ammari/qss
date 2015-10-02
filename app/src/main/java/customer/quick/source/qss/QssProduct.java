package customer.quick.source.qss;

/**
 * Created by abdul-rahman on 28/09/15.
 */
public class QssProduct {
    public String name;
    public int id;

    public QssProduct(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
