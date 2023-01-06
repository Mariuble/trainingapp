package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Session;
import treningsapp.core.User;

class UserSerializer extends JsonSerializer<User> {

  /*
   * format: 
   * 
   * { 
   *    "name": "...", 
   *    "passord": ...",
   *    "Sessions": [   ]   
   * }
   */


  @Override
  public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", user.getName());
    jsonGen.writeStringField("password", user.getPassword());
    jsonGen.writeArrayFieldStart("sessions");
    for (Session session : user) {
      jsonGen.writeObject(session);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}