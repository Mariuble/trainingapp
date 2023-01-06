package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import treningsapp.core.Session;
import treningsapp.core.User;

class UserDeserializer extends JsonDeserializer<User> {

  private SessionDeserializer sessionDeserializer = new SessionDeserializer();

  @Override
  public User deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }
  

  User deserialize(JsonNode jsonNode) {



    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      User user = new User();
      JsonNode nameNode = objectNode.get("name");
      if (nameNode instanceof TextNode) {
        user.setName(nameNode.asText());
      }
      JsonNode passordNode = objectNode.get("password");
      if (passordNode instanceof TextNode) {
        user.setPassword(passordNode.asText());
      }
      JsonNode sessionsNode = objectNode.get("sessions");
      if (sessionsNode instanceof ArrayNode) {
        for (JsonNode sessionNode : ((ArrayNode) sessionsNode)) {
          Session session = sessionDeserializer.deserialize(sessionNode);
          if (session != null) {
            user.addSession(session);
          }
        }
      }

      return user;
    }
    return null;
  }
}




