package treningsapp.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import treningsapp.core.Session;
import treningsapp.core.User;
import treningsapp.core.UserHandler;
import treningsapp.json.JacksonModule;

/**
 * This class will connect the rest api with the rest of the app.
*/
public class TreningsAppAccess {

  private URI endpointUri;
  private UserHandler userHandler;
  private ObjectMapper objectMapper;

  /**
   * Constructor.
   *
   * @param endpointUri
   *
   */
  public TreningsAppAccess(URI endpointUri) {
    this.endpointUri = endpointUri;
    this.objectMapper = new ObjectMapper().registerModule(new JacksonModule());
    this.userHandler = getUserHandler();
  }
  
  /**
   * Gets the userhandler.
   */
  public UserHandler getUserHandler() {
    if (this.userHandler == null) {
      updateUserHandler();
    }
    return this.userHandler;
  }

  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  private URI treningsAppUri(String name) {
    return endpointUri.resolve(uriParam(name));
  }


  /**
   * Gets a user.
   *
   * @param username Username.
   * @param password User password.
   * @return The user with the corresponding username and password 
   *        (We assume this combination is unique).
   */
  public User getUser(String username, String password) {
    // To avoid sending unnecessary requests, we will check our cached version of userHandler first.
    if (userExists(username, password)) {
      System.out.println("User exists locally");
      return this.userHandler.getUser(username);
    }
    System.out.println("Updating from server..");
    updateUserHandler();
    if (userExists(username, password)) {
      System.out.println("User exists now");
      return this.userHandler.getUser(username);
    }
    return null;
  }

  /**
   * Sends a new request to the server.
   * 
   */

  private void updateUserHandler() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(endpointUri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();
    try {
      String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
      System.out.println("Response: " + response);
      this.userHandler = objectMapper.readValue(response, UserHandler.class);

    } catch (IOException | InterruptedException e) {
      System.err.println("Request error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public Boolean userExists(String username, String password) {
    return this.userHandler.checkExistingUser(username, password);
  }

  private Boolean sessionExists(String username, String title) {
    return (this.userHandler.getUser(username).getSession(title) != null);
  }

  /**
   * Gets a Session form an user.
   *
   * @param username username.
   * @param title session with the given title
   */
  public Session getSession(String username, String title) {
    if (sessionExists(username, title)) {
      return this.userHandler.getUser(username).getSession(title);
    }
    updateUserHandler();
    if (sessionExists(username, title)) {
      return this.userHandler.getUser(username).getSession(title);
    }
    return null;
  }

  /**
   * Adds a user.
   *
   * @param user the user to put to the serverside throws an exception if error occurs
   *
   * @throws Exception
   * 
   */
  public void putUser(User user) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      
      // Builds and and assigns a HttpRequest to the variable request
      HttpRequest request = HttpRequest.newBuilder(treningsAppUri(user.getName()))
              .header("Accept", "application/json")
              .header("Content-Type", "application/json; charset=UTF-8")
              .PUT(BodyPublishers.ofString(objectMapper.writeValueAsString(user))).build();
      

      // Sends the request
      client.send(request, BodyHandlers.ofString());
      
      this.userHandler.addUser(user);
    } catch (Exception e) {
      System.out.println("PutUser failed");
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Adds a session.
   *
   * @param user the user logged in
   *
   * @param session the session to add
   * 
   */
  public void putSession(User user, Session session) {
    user.addSession(session); // locally
    putUser(user); // server
  }

  /**
   * Deletes a session.
   *
   * @param user the user logged in
   * @param session the session to delete
   *
   */
  public void deleteSession(User user, Session session) {
    user.removeSession(session);
    putUser(user);
  }
}
