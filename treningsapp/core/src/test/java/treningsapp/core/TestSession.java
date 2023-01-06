package treningsapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSession {

  private Session session;

  @BeforeEach
  public void setUp() {
    this.session = new Session();
    session.setStartDate(LocalDateTime.parse("2020-11-01T14:53"));
    session.setEndDate(LocalDateTime.parse("2020-11-01T15:55"));
    session.setTitle("Skitur");
  }

  @Test
  public void testGetTitle() {
    assertEquals("Skitur", session.getTitle());
  }

  @Test
  public void testGetDates() {
    assertEquals(LocalDateTime.parse("2020-11-01T14:53"), session.getStartDate());
    assertEquals(LocalDateTime.parse("2020-11-01T15:55"), session.getEndDate());
  }

  @Test
  public void testToString() {
    assertEquals("Skitur - From: 2020-11-01 14:53 to 2020-11-01 15:55", session.toString());
  }
}
