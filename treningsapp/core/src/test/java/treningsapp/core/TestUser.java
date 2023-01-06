package treningsapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

  private User user;

  @BeforeEach
  public void setUp() {
    user = new User("Bruker", "passord1");
  }
  @Test
  public void testDefaultConstructor() {
    User emptyUser = new User();
    assertEquals("", emptyUser.getName());
    assertEquals("", emptyUser.getPassword());
  }
  @Test
  public void testConstructor() {
    assertEquals("Bruker", user.getName());
    assertEquals("passord1", user.getPassword());
  }

  @Test
  public void testGetPassword() {
    assertEquals("passord1", user.getPassword());
  }

  @Test
  public void testGetName() {
    assertEquals("Bruker", user.getName());
  }

  @Test
  public void testSetName() {
    user.setName("nyttNavn");
    assertEquals("nyttNavn", user.getName());
  }

  @Test
  public void testSetPassword() {
    user.setPassword("passord2");
    assertEquals("passord2", user.getPassword());
  }

  @Test
  public void testAddSessions() {
    assertFalse(user.iterator().hasNext());
    Session session = new Session("løpe",LocalDateTime.parse("2019-08-07T14:53:00"),LocalDateTime.parse("2019-08-07T14:54:00"));
    user.addSession(session);
    assertTrue(user.iterator().hasNext());
    Assertions.assertSame(user.iterator().next(), session);
  }
  
  @Test
  public void testToString() {
    assertEquals("User [name=Bruker, password=passord1, sessions=[]]", user.toString());
  }

  @Test
  public void testIterator() {
    Session session1 = new Session("løpe",LocalDateTime.parse("2019-08-07T14:53:00"),LocalDateTime.parse("2019-08-07T14:54:00"));
    Session session2 = new Session("løpe",LocalDateTime.parse("2019-08-08T14:53:00"),LocalDateTime.parse("2019-08-07T14:54:00"));
    user.addSession(session1);
    user.addSession(session2);

    Iterator<Session> it = user.iterator();
    assertTrue(it.hasNext());
    assertSame(session1, it.next());
    assertTrue(it.hasNext());
    assertSame(session2, it.next());
    assertFalse(it.hasNext());
  }
}
