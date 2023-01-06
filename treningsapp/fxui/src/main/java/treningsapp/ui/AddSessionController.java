package treningsapp.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import treningsapp.core.Session;
import treningsapp.core.User;

public class AddSessionController {

  // UserHandler userHandler;

  User tempUser;
  // FXML elements actionscreen
  @FXML
  private Pane actionScreen;
  @FXML
  private DatePicker datePicker;
  @FXML
  private Button btnSave;
  @FXML
  private Button btnLogout;
  @FXML
  private Button btnSignup;
  @FXML
  private TextField sessionName;
  @FXML
  private TextArea txtOutput;
  @FXML
  private Label lblActiveUser;
  @FXML
  private ComboBox<Integer> startHour;
  @FXML
  private ComboBox<Integer> endHour;
  @FXML
  private ComboBox<Integer> startMinute;
  @FXML
  private ComboBox<Integer> endMinute;
  @FXML
  private TableView<Session> tableView;
  @FXML
  private TableColumn<Session, String> sessionNameCol;
  @FXML
  private TableColumn<Session, LocalDateTime> startTimeCol;
  @FXML
  private TableColumn<Session, LocalDateTime> endTimeCol;
  @FXML
  private TableColumn<Session, LocalDateTime> dateCol;
  

  @FXML
  private Button btnDeleteRows;

  ObservableList<Session> temUserList;


  /**
   * First method to run.
   */
  public void initialize() {
    ControllerMediator.getInstance().registerController(this);
    datePicker.getEditor().setDisable(true);
    setChoiceBox();
  }

  /**
   * Formattes the chosen TableColumn with DateTimeFormatter.
   *
   * @param collum tableColum which represent a LocalDateTime og the Session class.
   */

  public void dateCollumFormatter(TableColumn<Session, LocalDateTime> collum, String patter) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patter);
    collum.setCellFactory(column -> new TableCell<Session, LocalDateTime>() {
      @Override
      protected void updateItem(LocalDateTime date, boolean empty) {
        super.updateItem(date, empty);
        if (empty) {
          setText(null);
        } else {
          setText(formatter.format(date));
        }
      }
    });
  }

  /**
   * Update the TableView for the tempUsers sessions.
   */

  public void updateUserInfo() {
    this.tempUser = ControllerMediator.getInstance().getUserHandler().getTempUser();
    List<Session> userList = new ArrayList<Session>();
    for (Session session : this.tempUser) {
      userList.add(session);
    }
    this.temUserList = FXCollections.observableArrayList(userList);
    sessionNameCol.setCellValueFactory(new PropertyValueFactory<Session, String>("title"));
    dateCollumFormatter(startTimeCol, "HH:mm");
    dateCollumFormatter(endTimeCol, "HH:mm");
    dateCollumFormatter(dateCol, "dd.MM.yyyy");
    startTimeCol.setCellValueFactory(new PropertyValueFactory<Session, LocalDateTime>("startDate"));
    endTimeCol.setCellValueFactory(new PropertyValueFactory<Session, LocalDateTime>("endDate"));
    dateCol.setCellValueFactory(new PropertyValueFactory<Session, LocalDateTime>("startDate"));
    tableView.setItems(temUserList);
    updateUserLabel(tempUser);
    setSessionTime();
  }

  /**
   * Updates the label which represent the logged in user.
   *
   * @param tempUser the logged in user.
   */

  public void updateUserLabel(User tempUser) {
    lblActiveUser.setText("User: " + tempUser.getName());
  }

  /**
   * Updates the choiceBoxes with time intervals.
   */
  @FXML
  public void setChoiceBox() {
    List<Integer> hours = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList());
    List<Integer> minutes = IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList());
    startHour.getItems().addAll(hours);
    endHour.getItems().addAll(hours);
    startMinute.getItems().addAll(minutes);
    endMinute.getItems().addAll(minutes);
  }

  /**
   * Sets the ComboBoxs to the current time.
   */

  public void setSessionTime() {
    datePicker.setValue(LocalDate.now());
    LocalDateTime date = LocalDateTime.now();
    int hours = date.getHour();
    int minutes = date.getMinute();
    startHour.setValue(hours);
    startMinute.setValue(minutes);
    endHour.setValue(hours);
    endMinute.setValue(minutes);
  }

  /**
   * Saves the session to the useer that is logged in.
   */
  @FXML
  public void saveSessionFromUi() {
    LocalDate date = datePicker.getValue();
    LocalDateTime startDate = date.atTime(startHour.getValue(), startMinute.getValue());
    LocalDateTime endDate = date.atTime(endHour.getValue(), endMinute.getValue());
    String title = sessionName.getText();
    String feedbackMessage = ControllerMediator.getInstance().getUserHandler()
        .verifySession(title, startDate, endDate);
    if (!(feedbackMessage.isEmpty())) {
      System.out.println(feedbackMessage);
    } else {
      Session session = new Session(title, startDate, endDate);
      ControllerMediator.getInstance()
      .getRegisterController()
      .getTreningsAppAccess()
              .putSession(tempUser, session);
      datePicker.setValue(null);
      sessionName.clear();
      updateUserInfo();
    }
  }

  /**
   * Loggs the user out of the program.
   */
  @FXML
  public void logoutFromUi() {
    ControllerMediator.getInstance().getUserHandler().logout();
    ControllerMediator.getInstance()
            .getRegisterController()
            .loginScreen.setVisible(true);
    this.tempUser = null;
  }

  /**
   * Removes the selected row from the TableView.
   *
   * @param event mouse click.
   */
  @FXML
  public void deleteSelectedRow(ActionEvent event) {
    Session session = tableView.getSelectionModel().getSelectedItem();
    ControllerMediator.getInstance()
            .getRegisterController()
            .getTreningsAppAccess()
            .deleteSession(tempUser, session);
    tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
  }
}
