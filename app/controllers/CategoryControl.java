package controllers;

import models.Category;
import models.Section;
import play.api.mvc.Controller;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Result;
import views.html.category;
import views.html.section;

import java.util.List;

/**
 * Created by TREXX on 23/08/14.
 */
public class CategoryControl extends play.mvc.Controller {

    public static Result index() {
        List<Category> cat = new Model.Finder(String.class, Category.class).all();
        return ok(category.render("ready", cat));
    }

    public static Result addCategory(){
        Category cat = Form.form(Category.class).bindFromRequest().get();
        cat.save();
        return redirect(routes.CategoryControl.index());
    }

    public List<Category> manageCategory(){
        List<Category> category = new Model.Finder(String.class, Category.class).all();
        return category;
    }

    public Category getCategoryById(String id){
        Category category = Category.find.where().eq("id",id).findUnique();
        return category;
    }

    public static Result deleteCategory(String id){
        List<Category> category = new Model.Finder(String.class, Category.class).all();
        return ok(Json.toJson(category));
    }
}
