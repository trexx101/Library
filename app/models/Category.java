package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by TREXX on 21/08/14.
 */
@Entity
public class Category extends Model{

    @Id
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Finder<String,Category> find = new Finder<String,Category>(
            String.class, Category.class
    );
}
