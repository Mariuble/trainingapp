package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import treningsapp.core.User;
import treningsapp.core.UserHandler;

class UserHandlerDeserializer extends JsonDeserializer<UserHandler> {

  private UserDeserializer userDeserializer = new UserDeserializer();
  /*
   * format: { "lists": [ ... ] }
   */

  @Override
  public UserHandler deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  UserHandler deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) treeNode;
      UserHandler userHandler = new UserHandler();
      JsonNode usersNode = objectNode.get("users");
      if (usersNode instanceof ArrayNode) {
        for (JsonNode elementN : ((ArrayNode) usersNode)) {
          User user = userDeserializer.deserialize(elementN);
          if (user != null) {
            userHandler.addUser(user);
          }
        }
      }
      return userHandler;
    }
    return null;
  }
}

