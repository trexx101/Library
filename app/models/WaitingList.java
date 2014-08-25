package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by TREXX on 25/08/14.
 */
@Entity
public class WaitingList  extends Model {
    @Id
    private String id;
    private  Resource resourceId;
    private User userId;
    private Date date;

    public Resource getResourceId() {
        return resourceId;
    }

    public void setResourceId(Resource resourceId) {
        this.resourceId = resourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public WaitingList(Resource resourceId, User userId, Date date) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.date = date;
    }

    public static Finder<String,WaitingList> find = new Finder<String,WaitingList>(String.class, WaitingList.class);

}
