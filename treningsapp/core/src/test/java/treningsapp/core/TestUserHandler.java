package treningsapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUserHandler {

  private UserHandler userHandler;

  @BeforeEach
  public void setUp() {
    this.userHandler = new UserHandler();
    userHandler.addUser(new User("User1", "password"));
    userHandler.addUser(new User("User2", "password"));
    userHandler.addUser(new User("User3", "password"));
  }

  @Test
  public void testGetUserCount(){
    assertEquals(3,userHandler.getUserCount());
    userHandler.addUser(new User("User4", "password"));
    assertEquals(4, userHandler.getUserCount());
  }

  @Test
  public void testGetUser(){
    final User someUser = new User("someUser", "password");
    userHandler.addUser(someUser);
    assertSame(someUser, userHandler.getUser("someUser"));
    assertNotNull(userHandler.getUser("User1"));
    assertNotNull(userHandler.getUser("User2"));
    assertNotNull(userHandler.getUser("User3"));
    assertNull(userHandler.getUser("NonExistingUser"));
    assertEquals(null, userHandler.getUser("nonUser"));
  }

  // Tests if a premade user in the JSON file exists in the program.
  @Test
  public void testCheckExistingUser() {
    assertTrue(userHandler.checkExistingUser("User1", "password"));
    assertTrue(userHandler.checkExistingUser("User2", "password"));
    assertTrue(userHandler.checkExistingUser("User3", "password"));
    assertFalse(userHandler.checkExistingUser("User4", "password"));
  }

  @Test
  public void testRemoveUser() {
    User userToBeRemoved = new User("removeUser", "password");
    User userToBeRemoved2 = new User("removeUser2", "password2");
    userHandler.addUser(userToBeRemoved);
    userHandler.addUser(userToBeRemoved2);
    assertTrue(userHandler.checkExistingUser("removeUser"));
    assertTrue(userHandler.checkExistingUser("removeUser2"));
    userHandler.removeUser(userToBeRemoved);
    userHandler.removeUser("removeUser2");
    assertFalse(userHandler.checkExistingUser("removeUse", "password"));
    assertThrows(NullPointerException.class,() -> userHandler.removeUser("NonExistingUser"));
  }
  @Test
  public void testTempuser(){
    assertFalse(userHandler.checkExistingUser("NonExistingUser"));
    assertTrue(userHandler.checkExistingUser("User1"));
    assertTrue(userHandler.checkExistingUser("User2"));
    assertFalse(userHandler.checkExistingUser("User2","notPassword"));
    assertTrue(userHandler.checkExistingUser("User3","password"));
  }

  @Test
  public void testLogin(){
    assertTrue(userHandler.login("User1", "password"));
    assertEquals(userHandler.getUser("User1"), userHandler.getTempUser());
    assertFalse(userHandler.login("NonExistingUser", "invalid"));
    assertEquals(userHandler.getUser("User1"), userHandler.getTempUser());
  }

  @Test
  public void testSignUpUser(){
    assertEquals("Username must at least be 4 charactes", userHandler.verifyNewUser("New","newPassword"));
    assertEquals("Password must at least be 6 characters", userHandler.verifyNewUser("NewUser","word"));
    assertEquals("Username is taken. Try again", userHandler.verifyNewUser("User1", "password"));
    assertEquals("", userHandler.verifyNewUser("NewUser", "newPassword"));
    userHandler.signUpUser("NewUser", "newPassword");
    assertTrue(userHandler.checkExistingUser("NewUser"));
  }
}
