package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 21/09/15.
 */
public class ProductORM extends SugarRecord<ProductORM>{
    private String name;

    public ProductORM() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
