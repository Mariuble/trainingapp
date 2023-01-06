package treningsapp.restserver;


import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import treningsapp.core.User;
import treningsapp.core.UserHandler;
import treningsapp.json.AppPersistence;
import treningsapp.restapi.TreningsAppService;

public class TreningsAppConfig extends ResourceConfig {

  private UserHandler userHandler;
  

  public void setUserHandler(UserHandler userHandler) {
    this.userHandler = userHandler;
  }
  
  public UserHandler getUserHandler() {
    return this.userHandler;
  }


  /**
   * Initialize the TreningsAppConfig.
   *
   * @param userHandler treningsApp-module instance to serve.
   *
   */
  public TreningsAppConfig(UserHandler userHandler) {
    setUserHandler(userHandler);
    register(TreningsAppService.class);
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(TreningsAppConfig.this.userHandler);
      }
    }); 
  }

  /**
   * Creates a default config with the defaultConfig.
   */
  public TreningsAppConfig() {
    this(defaultConfig());
  }

  /**
   * Creates a default config with an url to the default json file. then add an user to the file.
   */
  private static UserHandler defaultConfig() {
    AppPersistence appPersistence = new AppPersistence();
    URL url = TreningsAppConfig.class.getResource("default-treningsapp.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        UserHandler userHandler1 = appPersistence.readUserHandler(reader);
        return userHandler1;
      } catch (Exception e) {
        System.out.println("Couldn't read default treningsApp.json," 
                  + "so rigging treningsApp manually" + e);
      }
    }
    UserHandler userHandler = new UserHandler();
    userHandler.addUser(new User("user1", "password1"));
    userHandler.addUser(new User("user2", "password2"));
    return userHandler;
  }
}