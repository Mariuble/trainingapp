package treningsapp.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Session {

  private String title;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public Session() {
  }

  /**
   * Constructor.
   *
   * @param title Title of the sission
   * @param start Start time of the session
   * @param end   End time of the session
   */

  public Session(String title, LocalDateTime start, LocalDateTime end) {
    this.startDate = start;
    this.endDate = end;
    this.title = title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setStartDate(LocalDateTime date) {
    this.startDate = date;
  }

  public void setEndDate(LocalDateTime date) {
    this.endDate = date;
  }

  public void setDates(LocalDateTime startDate, LocalDateTime endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LocalDateTime getStartDate() {
    return this.startDate;
  }

  public LocalDateTime getEndDate() {
    return this.endDate;
  }

  public String getTitle() {
    return this.title;
  }

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return title + " - From: " + startDate.format(formatter) + " to " + endDate.format(formatter);
  }

}
