package treningsapp.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class User implements Iterable<Session> {
  private String name;
  private String password;
  private List<Session> sessions;

  /**
   * Empty instructor, where the list with sessions objects is made.
   */
  public User() {
    this.name = "";
    this.password = "";
    this.sessions = new ArrayList<Session>();
  }

  /**
   * Instructor, where the user object is made with an username,
   * password and an empty list with sessions objects.
   *
   * @param name     brukernavn til user objekt.
   * @param password passord yil user objektet.
   */

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    this.sessions = new ArrayList<Session>();
  }
  /** Removes a session object from the sesssionlist.
   *
   *@param session the session object wich will be removed. 
   */

  public void removeSession(Session session) {
    if (sessions.contains(session)) {
      sessions.remove(session);
    }  
  
  }


  public String getName() {
    return name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Session getSession(String title) {
    return sessions.stream().filter(u -> u.getTitle().equals(title)).findAny().orElse(null);
  }

  public Collection<Session> getSessions() {
    return new ArrayList<Session>(this.sessions);
  }

  public void addSession(Session session) {
    this.sessions.add(session);
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", password=" + password + ", sessions=" + sessions + "]";
  }

  @Override
  public Iterator<Session> iterator() {
    return this.sessions.iterator();
  }
}
