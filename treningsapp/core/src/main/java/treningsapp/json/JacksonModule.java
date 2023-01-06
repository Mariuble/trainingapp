package treningsapp.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import treningsapp.core.Session;
import treningsapp.core.User;
import treningsapp.core.UserHandler;

/**
 * This class helps the incapsulation of the serializers/deserializers.
 */

@SuppressWarnings("serial")
public class JacksonModule extends SimpleModule {

  private static final String NAME = "JacksonModule";

  /**
   * Initialize JaksonModule with the correct serializere and deserializere.
   */

  public JacksonModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Session.class, new SessionSerializer());
    addSerializer(User.class, new UserSerializer());
    addSerializer(UserHandler.class, new UserHandlerSerializer());
    addDeserializer(Session.class, new SessionDeserializer());
    addDeserializer(User.class, new UserDeserializer());
    addDeserializer(UserHandler.class, new UserHandlerDeserializer());

  }
}
