package controllers;

import models.Category;
import models.Resource;
import models.Section;
import models.User;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;
import java.util.Map;

public class Application extends Controller {

    public static Result index() {

        List<Resource> resource = Resource.find.where().eq("available", "true").findList();
        User user = UserControl.getUser();
        return ok(index.render(user, resource));
    }

    public static Result addResource(){
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String name = formData.get("name")[0];
        String author = formData.get("author")[0];
        String shelf = formData.get("shelf.getId")[0];
        System.out.println(shelf+" shelf id **************");
        String category = formData.get("section.getId")[0];

        //retrieve category and shelf objects
        Category cat = new CategoryControl().getCategoryById(category);
        Section sec = new SectionControl().getSectionById(shelf);

        Resource addres = new Resource(name, author, sec, cat);
        addres.save();
        System.out.println("Resource should be saved id: "+addres.getId());

        //Form res = Form.form(Resource.class).bindFromRequest();
        //res.get().save();
        return redirect(routes.Application.manageResource());
    }


    public static Result manageResource(){
        List<Category> cc= new CategoryControl().manageCategory();
        List<Section> sc= new SectionControl().manageSection();
        List<Resource> r = new Model.Finder(String.class, Resource.class).all();

        return ok(resource.render(sc, cc, r));
    }

    /*public static List<Resource> getResource(){
        List<Resource> resource = new Model.Finder(String.class, Resource.class).all();
        return resource;
    }*/

    public static Result searchResource(){
        //get parameter from posted
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String name = formData.get("name")[0];
        List<Resource> listR = Resource.find.where().like("name", "%"+name+"%").or().like("author", "%"+name+"%").findList();

        User user = UserControl.getUser();
        return ok(index.render(user, listR ));
    }

    public static Result borrowResource(String userId, String resourceId){
        System.out.println(" Attempting to borrow:" +resourceId);
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String name = formData.get("name")[0];
        List<Resource> listR = Resource.find.where().like("name", "%"+name+"%").findList();
        User user = UserControl.getUser();
        return ok(index.render(user,listR));
    }

    public static Result returnResource(String userId, String resourceId){
        System.out.println(" Attempting to borrow:" +resourceId);
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String name = formData.get("name")[0];
        List<Resource> listR = Resource.find.where().like("name", "%"+name+"%").findList();
        User user = UserControl.getUser();
        return ok(index.render(user,listR));
    }

}
