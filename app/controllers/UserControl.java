package controllers;

import models.User;
import play.mvc.Controller;


/**
 * Created by TREXX on 24/08/14.
 */
public class UserControl extends Controller {
    public void createNewUsers(){
        //create the new users since am to cool to create an actual login module
        String[] names = {"Gerrad Butler","Jennifer Aniston", "Ellen Degenress"};
        String[] emails = {"G-Butler@yahoo.com","J-Aniston@yahoo.com", "E-Degen@yahoo.com"};
        for(int x = 0;x < names.length; x++){
            User generateUser = new User(names[x],emails[x]);
            generateUser.save();
            System.out.println("registered user name"+names[x]+"  "+emails[x]+" "+generateUser.getId());
        }
        return ;
    }

    public void StoreUsers(){
        //create the new users since am to cool to create an actual login module
        String[] names = {"Gerrad Butler","Jennifer Aniston", "Ellen Degenress"};
        String[] emails = {"G-Butler@yahoo.com","J-Aniston@yahoo.com", "E-Degen@yahoo.com"};
        for(int x = 0;x < names.length; x++){
            User generateUser = new User(names[x],emails[x]);
            generateUser.save();
            System.out.println("registered user name"+names[x]+"  "+emails[x]+" "+generateUser.getId());
        }
        return ;
    }

    public static User getUser() {
        //get user from session
        String id = new SessionManager().get("login");
        User user = User.find.byId(id);
        return user;
    }
}
