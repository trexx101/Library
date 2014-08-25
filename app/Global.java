import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Http;

import java.util.List;
import java.util.Random;

/**
 * Created by TREXX on 24/08/14.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            String[] names = {"Gerrad Butler","Jennifer Aniston", "Ellen Degenress"};
            String[] emails = {"G-Butler@yahoo.com","J-Aniston@yahoo.com", "E-Degen@yahoo.com"};
            for(int x = 0;x < names.length; x++){
                User generateUser = new User(names[x],emails[x]);
                generateUser.save();
                Logger.info("registered user name"+names[x]+"  "+emails[x]+" "+generateUser.getId());

            }
        }

        //set profile in session
        List<User> users = User.find.all();
        Random randomno = new Random();

        // check next int value
        User randomUser = users.get(randomno.nextInt(users.size()));
        Http.Session session = Http.Context.current().session();
        session.put("login",randomUser.getId());
        Logger.info("User login with name ->"+randomUser.getName());

        //SessionManager sm;


    }
}