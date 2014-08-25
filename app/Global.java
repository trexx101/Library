import models.Category;
import models.Resource;
import models.Section;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;

/**
 * Created by TREXX on 24/08/14.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        // Check if User database table is empty
        if (User.find.findRowCount() == 0) {
            String[] names = {"Gerrad Butler","Jennifer Aniston", "Ellen Degenress"};
            String[] emails = {"G-Butler@yahoo.com","J-Aniston@yahoo.com", "E-Degen@yahoo.com"};
            for(int x = 0;x < names.length; x++){
                User generateUser = new User(names[x],emails[x]);
                generateUser.save();
                Logger.info("registered user name "+names[x]+"  "+emails[x]+" "+generateUser.getId());

            }
        }

        //populate Section table
        if (Section.find.findRowCount() == 0) {
            String[] names = {"Book","Magazine", "Newspaper"};
            boolean[] borrowable = {true,true, false};
            for(int x = 0;x < names.length; x++){
                Section generateSection = new Section(names[x],borrowable[x]);
                generateSection.save();
                Logger.info("registered resource section with name "+names[x]+"  "+borrowable[x]+" "+generateSection.getId());

            }
        }

        //Populate category table if empty
        if (Category.find.findRowCount() == 0) {
            String[] names = {"Kids","Entertainment", "Science"};
            for(int x = 0;x < names.length; x++){
                Category generateCategory = new Category(names[x]);
                generateCategory.save();
                Logger.info("registered category with name "+names[x]+" "+generateCategory.getId());

            }
        }

        //Populate Resource table if empty
        if (Resource.find.findRowCount() == 0) {
            Section[] section= new Section[3];
            //for(int x=0;x<2;x++){

            section[0] = Section.find.where().eq("name", "Book").findUnique();
            section[1] = Section.find.where().eq("name", "Newspaper").findUnique();
            section[2] = Section.find.where().eq("name", "Magazine").findUnique();

            Category[] category= new Category[3];
            category[0]  = Category.find.where().eq("name", "Kids").findUnique();
            category[1] = Category.find.where().eq("name", "Entertainment").findUnique();
            category[2] = Category.find.where().eq("name", "Science").findUnique();
            //}
            String[] names = {"The games of thrones","Dune", "The bible","oliver Twist","The daily planet", "Gotham globe","new straits times", "Time","Top gear", "GQ"};
            String[] author = {"george Martin","Frank Herbert", "John Baptiste","Charles Dickens", "Clark Kent","Bruce Wayne", "Dato sifu","Barack Obama", "Jeremy Clark", "Brad Pitt"};
            Section[] sec = {section[0],section[0],section[0],section[0],section[1],section[1],section[1],section[2],section[2],section[2]};
            Category[] cat = {category[1],category[1],category[2],category[0],category[0],category[2],category[1],category[2],category[1],category[1]};
            boolean[] avail = {true,true, true,true,true, true,true,true, true,true};
            for(int x = 0;x < names.length; x++){
                Resource generateResource = new Resource(names[x],author[x],sec[x],cat[x],avail[x]);
                generateResource.save();
                Logger.info("registered resource section with name "+names[x]+" and ID: "+generateResource.getId());

            }
        }

    }
}