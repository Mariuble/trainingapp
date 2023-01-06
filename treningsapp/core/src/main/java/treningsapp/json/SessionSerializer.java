package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Session;

class SessionSerializer extends JsonSerializer<Session> {

  /*
   * format: 
   * 
   * { 
   *    "title": "...", 
   *    "startDate": "...",
   *    "endDate": "..."  
   * }
   */

  @Override
  public void serialize(Session session, JsonGenerator jsonGen, 
      SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("title", session.getTitle());
    jsonGen.writeStringField("startDate", session.getStartDate().toString());
    jsonGen.writeStringField("endDate", session.getEndDate().toString());
    jsonGen.writeEndObject();
  }
}



