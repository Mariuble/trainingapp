package treningsapp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.time.LocalDateTime;
import java.util.Iterator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import treningsapp.core.Session;
import treningsapp.core.User;
import treningsapp.core.UserHandler;

public class TestJacksonModule {

  private static ObjectMapper mapper;

  private final static String userWithTwoSessions =
    "{\"name\":\"Ola\",\"password\":\"Nordmann\",\"sessions\":[{\"title\":\"løpetur\",\"startDate\":\"2020-07-07T14:53\",\"endDate\":\"2020-07-07T14:54\"},{\"title\":\"skitur\",\"startDate\":\"2019-08-07T14:53\",\"endDate\":\"2019-08-07T14:54\"}]}";
  private final static String userHandlerWithTwoUsers = "{\"users\":[{\"name\":\"Ola\",\"password\":\"Nordmann\",\"sessions\":[{\"title\":\"løpetur\",\"startDate\":\"2020-07-07T14:53\",\"endDate\":\"2020-07-07T14:54\"},{\"title\":\"skitur\",\"startDate\":\"2019-08-07T14:53\",\"endDate\":\"2019-08-07T14:54\"}]},{\"name\":\"Kari\",\"password\":\"Nordmann\",\"sessions\":" + 
    "[{\"title\":\"løpetur\",\"startDate\":\"2020-07-07T14:53\",\"endDate\":\"2020-07-07T14:54\"},{\"title\":\"skitur\",\"startDate\":\"2019-08-07T14:53\",\"endDate\":\"2019-08-07T14:54\"}]}]}";

  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JacksonModule());

  }

  @Test
  public void testSerializer() throws JsonProcessingException {

    User user = new User("Ola", "Nordmann");
    Session session1 = new Session();
    Session session2 = new Session();
    User user2 = new User ("Kari", "Nordmann");
    session1.setDates(LocalDateTime.parse("2020-07-07T14:53"), LocalDateTime.parse("2020-07-07T14:54"));
    session1.setTitle("løpetur");
    session2.setDates(LocalDateTime.parse("2019-08-07T14:53"),LocalDateTime.parse("2019-08-07T14:54"));
    session2.setTitle("skitur");

    user.addSession(session1);
    user.addSession(session2);
    user2.addSession(session1);
    user2.addSession(session2);
    UserHandler userHandler  = new UserHandler();
    userHandler.addUser(user);
    userHandler.addUser(user2);

    try {
      assertEquals(userWithTwoSessions.replaceAll("\\s+", ""), mapper.writeValueAsString(user));
      assertEquals(userHandlerWithTwoUsers.replaceAll("\\s+", ""), mapper.writeValueAsString(userHandler));

    } catch (JsonProcessingException e) {
      fail();
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Test
  public void testDeSerializer() {
    try {
      UserHandler userHandler = mapper.readValue(userHandlerWithTwoUsers, UserHandler.class);
      System.out.println(userHandler.getUserCount());
      Iterator <User> handlerIterator = userHandler.iterator();
      assertTrue(handlerIterator.hasNext());
      User user1 = handlerIterator.next();
      Iterator <Session> sessionIterator = user1.iterator();
      assertTrue(user1.iterator().hasNext());
      Session session1 = sessionIterator.next();
      assertTrue(session1.getTitle().equals("løpetur"));
      assertEquals(session1.getStartDate(), LocalDateTime.parse("2020-07-07T14:53"));
      assertEquals(session1.getEndDate(), LocalDateTime.parse("2020-07-07T14:54"));
      assertTrue(user1.getName().equals("Ola"));
      assertTrue(user1.getPassword().equals("Nordmann"));
      assertTrue(sessionIterator.hasNext());
      Session session2 = sessionIterator.next();
      assertTrue(session2.getTitle().equals("skitur"));
      assertEquals(session2.getStartDate(), LocalDateTime.parse("2019-08-07T14:53"));
      assertEquals(session2.getEndDate(), LocalDateTime.parse("2019-08-07T14:54"));
      assertFalse(sessionIterator.hasNext());
      assertTrue(handlerIterator.hasNext());
      User user2 = handlerIterator.next();
      assertTrue(user2.getName().equals("Kari"));
      assertTrue(user2.getPassword().equals("Nordmann"));
      assertFalse(handlerIterator.hasNext());
      Iterator <Session> sessionIterator2 = user2.iterator();
      assertTrue(sessionIterator2.hasNext());
      Session session3 = sessionIterator2.next();
      assertTrue(session3.getTitle().equals("løpetur"));
      assertEquals(session3.getStartDate(), LocalDateTime.parse("2020-07-07T14:53"));
      assertEquals(session3.getEndDate(), LocalDateTime.parse("2020-07-07T14:54"));
    } catch (JsonProcessingException exception) {
      fail();
    }

  }
}