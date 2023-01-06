package treningsapp.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import treningsapp.core.Session;
import treningsapp.core.User;
import treningsapp.core.UserHandler;

public class TestAppPersistence{


    private AppPersistence appPersistence = new AppPersistence();


    @Test
    public void testSerializersDeserializers(){


      UserHandler userHandler = new UserHandler();
      User user = new User("testbruker", "testpassord");
      Session session = new Session("testsession", LocalDateTime.parse("2020-07-07T14:53:11"), LocalDateTime.parse("2019-08-07T14:53:11"));
      user.addSession(session);
      userHandler.addUser(user);
      try {
        StringWriter writer = new StringWriter();
        appPersistence.writeUserHandler(writer, userHandler);
        String stringjson = writer.toString(); // string version of the userHandler we set up above
        UserHandler userHandler2 = appPersistence.readUserHandler(new StringReader(stringjson)); // Makes an userHandler object from string
        Iterator<User> it = userHandler2.iterator();
        assertTrue(it.hasNext());
        User user2 = it.next();
        assertFalse(it.hasNext());
        assertTrue(user2.getName().equals("testbruker") && user2.getPassword().equals("testpassord"));
        Iterator<Session> it2 = user2.iterator();
        assertTrue(it2.hasNext());
        Session session2 = it2.next();
        assertTrue(session2.getTitle().equals("testsession"));
        assertTrue(session2.getStartDate().isEqual(LocalDateTime.parse("2020-07-07T14:53:11")) && session2.getEndDate().isEqual(LocalDateTime.parse("2019-08-07T14:53:11")));
        assertFalse(it2.hasNext());

      } catch (Exception e) {
        fail();
      }


    } 











}