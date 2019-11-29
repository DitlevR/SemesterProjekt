package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookDTO;
import dtos.UserDTO;
import dtos.UserPassDTO;
import entities.Book;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.BookFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("user")
public class UserResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    private static final UserFacade USERFACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String book() {

        return "{\"User\":" + "}";
    }
    
    @Path("register")
    @POST
    //@Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String registerUser(String user) throws MissingInputException {
        UserPassDTO userpassdto = GSON.fromJson(user, UserPassDTO.class);
        User u = USERFACADE.createUser(userpassdto.getName(), userpassdto.getUserpass());
        
        return GSON.toJson(new UserDTO(u));
    }

    @Path("allusers")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllUsers() {

        List<User> allusers = USERFACADE.getAllUsers();
        List<UserDTO> dtos = new ArrayList<>();
        for (int i = 0; i < allusers.size(); i++) {
            dtos.add(new UserDTO(allusers.get(i)));
        }

        return GSON.toJson(dtos);

    }

    @Path("/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUser(@PathParam("username") String username) throws NotFoundException {

        UserDTO user = new UserDTO(USERFACADE.getUser(username));

        return GSON.toJson(user);

    }
    

}
