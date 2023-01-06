package treningsapp.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import treningsapp.json.JacksonModule;


/**
 * Jacksons ObjectMapper Helps serialize/ deserialize json string and java objects.
 */

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
  private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JacksonModule());

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}