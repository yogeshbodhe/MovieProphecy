/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import twitter4j.TwitterException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * REST Web Service
 *
 * @author AravindKumarReddy
 */
@Path("mp")
public class MovieProphecy {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MovieProphecy
     */
    public MovieProphecy() {
    }

    /**
     * Retrieves representation of an instance of mp.MovieProphecy
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText(@QueryParam("name") String movieName) throws IOException, TwitterException, ClassNotFoundException, SQLException {
        //TODO return proper representation object
        if(movieName!=null)
        {
        return mp.SearchTweets.search(movieName);        
        }
        else return "-1";
    }

    /**
     * PUT method for updating or creating an instance of MovieProphecy
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
