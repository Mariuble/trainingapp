package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.time.LocalDateTime;
import treningsapp.core.Session;

class SessionDeserializer extends JsonDeserializer<Session> {


  @Override
  public Session deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Session deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      Session session = new Session();
      JsonNode textNode = objectNode.get("title");
      if (textNode instanceof TextNode) {
        session.setTitle(textNode.asText());
      }
      JsonNode startDateNode = objectNode.get("startDate");
      if (startDateNode instanceof TextNode) {
        session.setStartDate(LocalDateTime.parse(startDateNode.asText()));
      }
      JsonNode endDateNode = objectNode.get("endDate");
      if (endDateNode instanceof TextNode) {
        session.setEndDate(LocalDateTime.parse(endDateNode.asText()));
      }
      return session;
    }
    return null;
  }
}
