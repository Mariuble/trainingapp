package treningsapp.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import treningsapp.core.UserHandler;

public class AppPersistence {


  ObjectMapper mapper;

  public AppPersistence() {
    this.mapper = new ObjectMapper();
    mapper.registerModule(new JacksonModule());
  }


  public UserHandler readUserHandler(Reader reader) throws IOException {
    return mapper.readValue(reader, UserHandler.class);
  }

  public void writeUserHandler(Writer writer, UserHandler userHandler)throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, userHandler);
  }


  public UserHandler readDataClass(StringReader stringReader) {
    return null;
  }

}