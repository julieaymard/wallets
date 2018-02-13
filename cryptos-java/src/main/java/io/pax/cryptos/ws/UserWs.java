package io.pax.cryptos.ws;

import io.pax.cryptos.dao.UserDao;
import io.pax.cryptos.domain.*;
import io.pax.cryptos.domain.jdbc.FullUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by AELION on 09/02/2018.
 */

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class UserWs {
    @GET
    public List<User> getUser() throws SQLException {
        UserDao dao = new UserDao();
        return dao.listUser();

    }

    @GET
    @Path("{id}") // this is a path param
    public User getUser(@PathParam("id") int userId) throws SQLException {

        return new UserDao().findUserWithWallet(userId);

    }

    @POST
    /*return future user with an id*/
    public User createWallet(FullUser user /* sent wallet, has no idea*/) {
        List<Wallet> wallets = user.getWallets();

        if (user.getName().length() < 2) {
            throw new NotAcceptableException("406: User name must have at least 2 letters");
        }
        try {
            int id = new UserDao().createUser(user.getName());


            return new FullUser(id, user.getName(), user.getWallets());
        } catch (SQLException e) {
            throw new ServerErrorException("Database error, sorry",500);
        }
    }
}
