package customer.quick.source.qss.ObjectsORM;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 16/09/15.
 */
public class RewardORM extends SugarRecord<RewardORM> {

    private String title;
    private String Description;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public RewardORM() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
