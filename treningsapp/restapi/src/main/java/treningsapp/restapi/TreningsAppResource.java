package treningsapp.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import treningsapp.core.User;
import treningsapp.core.UserHandler;


/**
 * This class is used when httpRequests contains username.
 */
public class TreningsAppResource {

  private final UserHandler userHandler;
  private final String username;
  
  /**
   * Constructor.
   *
   * @param userHandler is final
   * @param username is final
   *
   */
  public TreningsAppResource(final UserHandler userHandler, final String username) {
    this.userHandler = userHandler;
    this.username = username;
  }

  private void checkUser() {
    if (this.userHandler.getUser(this.username) == null) {
      throw new IllegalArgumentException("User dont exist");
    }
  }

  /**
   * Gets a user.
   *
   * @return corresponding user
   *
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser() {
    checkUser();
    return this.userHandler.getUser(username);
  }

  /**
   * Replaces or adds the user.
   *
   * @param user the user to add
   *
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON) // input
  @Produces(MediaType.APPLICATION_JSON) // result
  public void putUser(final User user) {
    final User oldUser = this.userHandler.getUser(user.getName());
    removeUser(oldUser);
    addUser(user); // Also checks if user is contained in userHandler
  }

  private void addUser(final User user) {
    this.userHandler.addUser(user);
  }

  private void removeUser(final User user) {
    this.userHandler.removeUser(user);
  }
}