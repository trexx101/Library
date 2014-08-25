package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by TREXX on 21/08/14.
 */
@Entity
public class Resource extends Model{

    @Id
    private String id;

    private String name;

    private String author;

    @ManyToOne
    private Section section;
    @ManyToOne
    private Category shelf;

    private User borrowedBy;

    private boolean available= new Boolean(true);

    //@ManyToMany(cascade = CascadeType.REMOVE)
    //public List<User> waitingList = new ArrayList<User>();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Category getShelf() {
        return shelf;
    }

    public void setShelf(Category shelf) {
        this.shelf = shelf;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    public Resource(String name, String author, Section section, Category shelf, Boolean available) {
        this.name = name;
        this.author = author;
        this.section = section;
        this.shelf = shelf;
        this.setAvailable(available);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Finder<String,Resource> find = new Finder<String,Resource>(String.class, Resource.class);

    @Override
    public String toString() {
        return "models.Resource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", available=" + available +
                '}';
    }
}
