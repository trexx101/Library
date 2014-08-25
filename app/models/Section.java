package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by TREXX on 21/08/14.
 */
@Entity
public class Section extends Model{
    @Id
    private String id;
    private String name;
    private boolean borrowable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Section(String name, boolean borrowable) {
        this.name = name;
        this.borrowable = borrowable;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(boolean borrowable) {

        this.borrowable = borrowable;
    }

    public static Finder<String,Section> find = new Finder<String,Section>(
            String.class, Section.class
    );


}
