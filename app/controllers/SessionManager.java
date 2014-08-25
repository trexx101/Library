package controllers;


//import flexjson.JSONDeserializer;
//import flexjson.JSONSerializer;
import play.Logger;
import play.mvc.Http;
import play.mvc.Http.Session;
/**
 * Created by TREXX on 24/08/14.
 */
public class SessionManager {



    public void addSession(String key, String value) {

        if(value != null) {
            Session session = Http.Context.current().session();
            session.put(key, value);
        } else {
            Logger.info("Value for " + key + " is null");
        }
    }

    public String get(String key) {

        Session session = Http.Context.current().session();
        final String value = session.get(key);

        if (value == null) {
            return null;
        }

        return value;
    }

}