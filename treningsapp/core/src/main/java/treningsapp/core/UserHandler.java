package treningsapp.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class holds the information of the systems useres, and which user is logged in.
 */
public class UserHandler implements Iterable<User> {

  private List<User> users = new ArrayList<>();

  // Tells which user is logged in, else null.
  private User tempUser;

  /**
   * Add an object to the list.
   *
   * @param user user to add to the list.
   */
  public void addUser(User user) {
    if (!users.contains(user)) {
      users.add(user);
    }
  }

  public int getUserCount() {
    return this.users.size();
  }

  /**
   * Removes an user object from the list.
   *
   * @param user user to remove from the list.
   */

  public void removeUser(User user) {
    users.remove(user);
  }

  /**
   * Removes an user object from the list based on the username.
   *
   * @param username Username you want to remove.
   */
  public void removeUser(String username) {
    User u = getUser(username);
    if ((u == null)) {
      throw new NullPointerException();
    }
    users.remove(u);
  }

  /**
   * Returns an user object by searching on an username.
   *
   * @param username The username to the user object you want to return.
   */

  public User getUser(String username) {
    return users.stream().filter(u -> u.getName().equals(username)).findAny().orElse(null);
  }

  public Iterator<User> iterator() {
    return users.iterator();
  }

  /**
   * Checks if the username and password exist.
   *
   * @param name     username of the user
   * @param password the password of the user
   * @return true if the username exist with the given password
   */
  public boolean checkExistingUser(String name, String password) {
    return this.users.stream()
                .anyMatch(u -> (u.getName()
                .equals(name) && u.getPassword().equals(password)));
  }

  public boolean checkExistingUser(String name) {
    return !(this.getUser(name) == null);
  }

  public User getTempUser() {
    return this.tempUser;
  }

  public void setTempUser(User user) {
    this.tempUser = user;
  }


  /**
   * Logs a user into the application. Checks first if the the user exists in the system,
   * then proceed
   * login.
   *
   * @param username username of the user
   * @param password the password of the user
   * @return true if login was successfull, false otherwise.
   */
  public boolean login(String username, String password) {
    User u = getUser(username);
    if (this.checkExistingUser(username, password)) {
      setTempUser(u);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Registrers a new user to the system with a given username and password.
   * Checks for valid username and password, before creating the new user. 
   * If valid information is given, the user will be
   * logged in and user information saved to file.
   *
   * @param username username of the user
   * @param password the password of the user
   */

  public void signUpUser(String username, String password) {
    addUser(new User(username, password));
  }

  /**
   * Verifyes if the user has entered a valid username and password.
   *
   * @param username username of the user
   * @param password the password of the user
   * @return a String specifying what was invalid, or an empty String if valid.
   */
  public String verifyNewUser(String username, String password) {
    if (username.length() < 4) {
      return "Username must at least be 4 charactes";
    } else if (password.length() < 6) {
      return "Password must at least be 6 characters";
    } else if (checkExistingUser(username)) {
      return "Username is taken. Try again";
    } else {
      return "";
    }
  }

  /**
   * Logs out the temporary user.
   *
   * @throws IllegalStateException if there is no temporary user logged in.
   */
  public void logout() {
    if (tempUser == null) {
      throw new IllegalStateException("No temporary user to log out.");
    } else {
      setTempUser(null);
    }
  }


  public void saveSession(String t, LocalDateTime s, LocalDateTime e) {
    getTempUser().addSession(new Session(t, s, e));
  }

  /**
   * Verifyes the Session that the user wants to save.
   *
   * @param t Title of the sission
   * @param s Start time of the session
   * @param e End time of the session
   * @return a String specifying what was invalid, or an empty String if valid.
   */

  public String verifySession(String t, LocalDateTime s, LocalDateTime e) {
    if (e.isBefore(s)) {
      return "The end time cannot be before the start time";
    } else if (t.isBlank()) {
      return "The title can not be empty";
    } else {
      return "";
    }
  }
}
