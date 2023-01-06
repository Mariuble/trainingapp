package treningsapp.restserver;



import treningsapp.core.Session;

// Imports
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import treningsapp.core.User;
import treningsapp.core.UserHandler;
import treningsapp.restapi.TreningsAppService;




public class ServerTest extends JerseyTest {

  private ObjectMapper objectMapper;

  //Setter opp configen.
  @Override
  protected ResourceConfig configure() {
    final TreningsAppConfig config = new TreningsAppConfig();

    return config;
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    objectMapper = new ObjectMapperProvider().getContext(getClass());
  }

  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Tests if we get the right response(200) and checks if the information is correct
   */

  @Test
  public void testGetUser(){
    Response response = target(TreningsAppService.TreningsAppPath)
    .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
    .get();
    assertEquals(200, response.getStatus());
    try {
      UserHandler userHandler = objectMapper.readValue(response.readEntity(String.class), UserHandler.class);
      Iterator<User> iterator = userHandler.iterator();
      assertTrue(iterator.hasNext());
      User user1 = iterator.next();
      assertEquals("user1", user1.getName());
      assertEquals("password1", user1.getPassword());
      assertTrue(iterator.hasNext());
      User user2 = iterator.next();
      assertEquals("user2", user2.getName());
      assertEquals("password2", user2.getPassword());
      Iterator<Session> sessionIt = user1.iterator();
      assertTrue(sessionIt.hasNext());
      Session session1 = sessionIt.next();
      assertEquals("Bakkeløp", session1.getTitle());
      assertTrue(sessionIt.hasNext());
      Session session2 = sessionIt.next();
      assertEquals("Skitur", session2.getTitle());
    } 
    catch (Exception e) {
      fail(e.getMessage());
    }
  }

   @Test
  public void testGet_User_user1() {
    Response response = target(TreningsAppService.TreningsAppPath).path("user1").request().get();
    assertEquals(200, response.getStatus());
    try {
      User user1 = objectMapper.readValue(response.readEntity(String.class), User.class);
      assertEquals("user1", user1.getName());
      Iterator<Session> sessionIt = user1.iterator();
      assertTrue(sessionIt.hasNext());
      Session session1 = sessionIt.next();
      assertEquals("Bakkeløp", session1.getTitle());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

}