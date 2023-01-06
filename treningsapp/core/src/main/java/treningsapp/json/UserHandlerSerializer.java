package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.User;
import treningsapp.core.UserHandler;

class UserHandlerSerializer extends JsonSerializer<UserHandler> {

  /*
   * format: { "users": [ ... ] }
   */

  @Override
  public void serialize(UserHandler userHandler, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeArrayFieldStart("users");
    for (User user : userHandler) {
      gen.writeObject(user);
    }
    gen.writeEndArray();
    gen.writeEndObject();

  }


}
