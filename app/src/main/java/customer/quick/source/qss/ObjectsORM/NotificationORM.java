package customer.quick.source.qss.ObjectsORM;



import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 26/07/15.
 */
public class NotificationORM extends SugarRecord<NotificationORM>{
    private String title;
    private String msg;
    private String type;
    private boolean seen=false;

    public NotificationORM() {
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

   /* public NotificationObject(String title, String msg ,String type) {
        this.title = title;
        this.msg = msg;
        this.type=type;
    }*/


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }









}
