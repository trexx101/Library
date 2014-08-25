package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Application extends Controller {

    private static boolean isLogin = false;

    public static Result index() {

        if (!isLogin){
            //set profile in session
            List<User> users = User.find.all();
            Random randomno = new Random();

            // check next int value
            User randomUser = users.get(randomno.nextInt(users.size()));
            Http.Session session = Http.Context.current().session();
            session.put("login",randomUser.getId());
            Logger.info("User login with name ->"+randomUser.getName());
            isLogin=true;
        }

        List<Resource> resource = Resource.find.where().eq("available", "true").findList();
        User user = null;
        try {
            user = UserControl.getUser();
        }catch (NullPointerException n){
            n.printStackTrace();
        }

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

        Resource addres = new Resource(name, author, sec, cat, true);
        addres.save();
        Logger.info("Resource should be saved id: " + addres.getId());

        //Form res = Form.form(Resource.class).bindFromRequest();
        //res.get().save();
        return redirect(routes.Application.manageResource());
    }


    public static Result manageResource(){
        Logger.info("Resource content has been loaded ");
        List<Category> cc= new CategoryControl().manageCategory();
        List<Section> sc= new SectionControl().manageSection();
        List<Resource> r = Ebean.find(Resource.class).fetch("section").fetch("shelf").findList();
        //List<Resource> r = new Model.Finder(String.class, Resource.class).all();

        return ok(resource.render(sc, cc, r));
    }

    public static Result deleteResource(String resourceId){
        Resource res = Ebean.find(Resource.class, resourceId);
        res.delete();
        Logger.info("Resource has been deleted ==> " + res.getName());
        return redirect(routes.Application.manageResource());
    }

    public static Result searchResource(){
        List<Resource> listR = null;
        //get parameter from posted
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String search = formData.get("name")[0];

        String delimiter= "-";
        if (search.contains(delimiter)){
            Logger.info("found delimeter");
            String[] resourceData = search.split(delimiter);

            //trim spaces and search
            String rName = resourceData[0].trim();
            String rAuthor = resourceData[1].trim();
            Logger.info("resource : "+rName+" || author : "+rAuthor);
            listR = Resource.find.where().and(com.avaje.ebean.Expr.ilike("name", "%" + rName + "%"), com.avaje.ebean.Expr.ilike("author", "%" + rAuthor + "%")).orderBy().desc("name").findList();
        }else{
            Logger.info("no delimeter found, search normally");
            listR = Resource.find.where().or(com.avaje.ebean.Expr.ilike("name", "%" + search + "%"), com.avaje.ebean.Expr.ilike("author", "%" + search + "%")).orderBy().desc("name").findList();
        }
        User user = UserControl.getUser();
        return ok(index.render(user, listR ));
    }

    //borrow a resource
    public static Result borrowResource(String userId, String resourceId){
        Logger.info(" Attempting to borrow:" + resourceId);
        //get resource
        Resource res = Ebean.find(Resource.class, resourceId);
        User user = Ebean.find(User.class, userId);

        //check if borrowable
        if(res.getSection().isBorrowable()){
            Logger.info("the resource is borrowable => "+res.getName());
            //update record to unavailable and set borrower
            try{
                res.setAvailable(false);
                res.setBorrowedBy(user);
                res.update();
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            Logger.info("Stop borrow process, cannot borrow "+res.getName()+" GO BUY YOURS!!");
        }
        //reload content
        List<Resource> resource = Resource.find.where().eq("available", "true").findList();
        Logger.info("borrow and redirect back");
        return ok(index.render(user,resource));
    }

    public static Result returnResource(String userId, String resourceId){
        //get objects
        Resource res = Ebean.find(Resource.class, resourceId);
        User user = Ebean.find(User.class, userId);
        //double check that this user did infact borrow that resource
        if(res.getBorrowedBy().getId()==user.getId()){
            //update resource as returned
            res.setAvailable(true);
            res.setBorrowedBy(null);
            res.update();

            //check waiting list and email user with the earliest booking date
            try {
                //WaitingList nextUser = Resource.find.where().eq("available", "true").findList()
                WaitingList nextUser = Ebean.find(WaitingList.class).where()
                        .ieq("resourceId", res.getId())
                        .orderBy("date asc").findUnique();
            }catch (NullPointerException n){
                //no users in waiting list for this resource...
                Logger.info("Caught nullPointerException: no user waiting for this resource");
            }
        }else{
            Logger.error("why would you do that??");
        }
        List<Resource> resource = Resource.find.where().eq("available", "true").findList();
        return ok(index.render(user,resource));
    }

    public static Result bookResource(String userId, String resourceId){
        //retrieve objects
        Resource res = Ebean.find(Resource.class, resourceId);
        User user = Ebean.find(User.class, userId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Logger.info(" Attempting to book Resource:" + res.getName()+" by user "+user.getName()+" at day/time "+date);

        //Simply add this booking to waiting list
        WaitingList bookedResource = new WaitingList(res, user, date);
        bookedResource.save();
        Logger.info(" Booking successful " + bookedResource.getId());

        return redirect(routes.Application.index());

    }

}
