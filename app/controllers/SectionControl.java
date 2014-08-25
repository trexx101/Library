package controllers;

import models.Resource;
import models.Section;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.section;

import java.util.List;

/**
 * Created by TREXX on 22/08/14.
 */
public class SectionControl extends Controller {

    public static Result index() {
        List<Section> sec = new Model.Finder(String.class, Section.class).all();
        return ok(section.render("ready", sec));
    }

    public static Result addSection(){
        Section sec = Form.form(Section.class).bindFromRequest().get();
        sec.save();
        return redirect(routes.SectionControl.index());
    }

    public static List<Section> manageSection(){
        List<Section> section = new Model.Finder(String.class, Section.class).all();
        return section;
    }

    public Section getSectionById(String id){
        Section section = Section.find.where().eq("id",id).findUnique();
        return section;
    }

    public static Result deleteSection(){
        List<Section> section = new Model.Finder(String.class, Section.class).all();
        return ok(Json.toJson(section));
    }
}
