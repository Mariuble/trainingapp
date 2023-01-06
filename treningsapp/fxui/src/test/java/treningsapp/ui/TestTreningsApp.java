package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import treningsapp.core.Session;
import treningsapp.core.User;



public class TestTreningsApp extends ApplicationTest {
  // UI-elements which is usefull with every test.
  private TextField txtUsername;
  private PasswordField txtPassword;
  private Button btnLogin;
  private Button deleteButton;
  private Button btnSignup;
  private Label lblWrongLogin;
  private Pane loginScreen;
  private Parent parent;
  private TreningsAppController controller;
  private TableView<Session> tableView;
  private ComboBox<Integer> startMinute;
  private ComboBox<Integer> endMinute;
  private ComboBox<Integer> startHour;
  private ComboBox<Integer> endHour;
  // Variables to test Sign Up and Login.
  private final static String SIGNUPUSER = "Signup_user";
  private final static String LOGINUSER = "user1";
  private final static String VALIDPASSWORD = "password1";


  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TreningsApp.fxml"));
    this.parent = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Setup with variables used in allmost every test. (seems to help with loading problems)
   */

  @BeforeEach
  public void setup() {
    sleep();
    lblWrongLogin = (Label) parent.lookup("#lblWrongLogin");
    loginScreen = (Pane) parent.lookup("#loginScreen");
    txtUsername = (TextField) parent.lookup("#txtUsername");
    txtPassword = (PasswordField) parent.lookup("#txtPassword");
    btnLogin = (Button) parent.lookup("#btnLogin");
    btnSignup = (Button) parent.lookup("#btnSignup");
    tableView = (TableView <Session>) parent.lookup("#tableView");
    try {
      ControllerMediator.getInstance().getUserHandler().removeUser(SIGNUPUSER);
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  /**
   * Suspend the callingthred from executing for 1 second.
   * Can help the javaFX Robot with loading problems. 
   */
  public void sleep(){
    try {
      Thread.sleep(1000); 
    } catch (Exception e) {
      fail("Could not sleep, thread interupted.");
    }
  }
  // -------------------------- GENERAL TESTS ------------------------------//
  /**
   * Tests if the controller exists, UserHandler is loaded and no temp user is set.
   */
  @Test
  public void testControllerBeforeInteraction() {
    assertNotNull(this.controller);
    sleep();
  }
  // -------------------------- SCREEN TESTS ------------------------------//
  /**
   * Checks if loginscreens setup is correct.
   */
  @Test
  public void testLoginScreenVisibleAtStartup() {
    assertTrue(loginScreen.isVisible());
    assertTrue(lblWrongLogin.getText().isEmpty());
    assertTrue(txtUsername.getText().isEmpty());
    assertTrue(txtPassword.getText().isEmpty());
    sleep();
  }

  /**
   * Checks if the users screen is correct after the login.
   */
  @Test
  public void testActionScreenAfterLogin() {
    loginUserInteraction(LOGINUSER, VALIDPASSWORD);
    Label lblActiveUser = (Label) parent.lookup("#lblActiveUser");
    String shouldBe = "User: " + LOGINUSER;
    assertEquals(shouldBe, lblActiveUser.getText());
    sleep();
  }
  // -------------------------- LOG IN TESTS ------------------------------//
  /**
   * Writes the correct username and password and press the login button. Login screen will be
   * invisible and the tmpUser will be set to the correct user.
   */
  @Test
  public void testLoginExistingUser() {
    loginUserInteraction(LOGINUSER, VALIDPASSWORD);
    assertFalse(loginScreen.isVisible());
    assertEquals(ControllerMediator.getInstance().getUserHandler().getTempUser(), ControllerMediator.getInstance().getUserHandler().getUser(LOGINUSER));
    sleep();
  }

  /**
   * Tries to login with and invalid user. The loginscreen should still be visible and an outputtext
   * should appear like this: "Wrong username or password... try again" in the label lblWrongLogin.
   */
  @Test
  public void testLoginInvalidUserFromUi() {
    assertTrue(lblWrongLogin.getText().isEmpty());
    loginUserInteraction("INVALID_USERNAME", "INVALID_PASSWORD");
    assertTrue(loginScreen.isVisible());
    assertTrue(lblWrongLogin.getText().equals("Wrong username or password... try again"));
  }

  /**
   * Method that helps the testLogin tests through the UI.
   *
   * @param username Username you want to try login with.
   * @param password Input password you want to try login with.
   */
  public void loginUserInteraction(String username, String password) {
    sleep();
    clickOn(txtUsername).write(username);
    clickOn(txtPassword).write(password);
    clickOn(btnLogin);
  }
  // -------------------------- SIGN UP TESTS ------------------------------//
  /**
   * Makes a new user. The user should be created and the user-screen should be visible.
   */
  @Test
  public void testSignUpUserValidUsernameFromUi() {
    signUpUserInteraction(SIGNUPUSER, VALIDPASSWORD);
    Label lblActiveUser = (Label) parent.lookup("#lblActiveUser");
    String shouldBe = "User: " + SIGNUPUSER;
    assertEquals(shouldBe, lblActiveUser.getText());
    sleep();
  }

  /**
   * Tries to create a new user with a too short username. Gives feedback to the user that the
   * password must be longer than 4 characters.
   */
  @Test
  public void testSignUpWithShortUsernameFromUi() {
    signUpUserInteraction("Abc", VALIDPASSWORD);
    assertEquals("Username must at least be 4 charactes", lblWrongLogin.getText());
    assertTrue(loginScreen.isVisible());
    sleep();
  }

  /**
   * Tries to create an user with a too short password. Will give the user feedback that the password
   * must be longer than 6 characters.
   */
  @Test
  public void testSignUpWithShortPasswordFromUi() {
    signUpUserInteraction(SIGNUPUSER, "123");
    assertEquals("Password must at least be 6 characters", lblWrongLogin.getText());
    assertTrue(loginScreen.isVisible());
    sleep();
  }

  /**
   * Tries to create a user with an username that allready exists. Gives feedback that the username
   * allready exists.
   */
  @Test
  public void testSignUpWithExistingUsernameFromUI() {
    signUpUserInteraction(LOGINUSER, VALIDPASSWORD);
    assertEquals("Username is taken. Try again", lblWrongLogin.getText());
    assertTrue(loginScreen.isVisible());
    sleep();
  }

  /**
   * Method that helps the signUp tests. By creating a new user through the UI.
   *
   * @param username Username which will be used.
   * @param password password which will be used.
   */
  public void signUpUserInteraction(String username, String password) {
    sleep();
    clickOn(txtUsername).write(username);
    clickOn(txtPassword).write(password);
    clickOn(btnSignup);
  }
  // ------------------------- SESSION TESTS ---------------------------//
  /**
   * Creates a new user and saves that users session. Fails if the session name does not correspond
   * with the input in the textfield.
   */
  @Test
  public void testSaveSession() {
    signUpUserInteraction(SIGNUPUSER, VALIDPASSWORD);
    assertTrue(tableView.getItems().isEmpty());
    addSessionInteraction("Skitur", 0, 0, 0, 1);
    assertEquals(1, tableView.getItems().size());
    assertEquals("Skitur", tableView.getItems().get(0).getTitle());
    assertTrue(tableView.getItems().get(0).getStartDate()
        .isBefore(tableView.getItems().get(0).getEndDate()));
          
    sleep();

  }

  /**
   * Tries to create a new user without a title. This should not give an update in the textfield.
   */
  @Test
  public void testSaveSessionEmptyTitle() {
    signUpUserInteraction(SIGNUPUSER, VALIDPASSWORD);
    addSessionInteraction("", 0, 0, 0, 0);
    ObservableList<Session> list = tableView.getItems();
    assertTrue(list.isEmpty());
    sleep();
  }

  /**
   * Method that helps the tests to add a session in the UI.
   * 
   * @param sessionText Session name.
   * @param startHour   Start hour of session.
   * @param startMin    Start minute of session.
   * @param endHour     End hour of session.
   * @param endMin      End minute of session.
   * @throws InterruptedException
   */

  // For å unngå unchecked convensions feilmelding i parent.lookup for ComboBox.
  @SuppressWarnings("unchecked")
  public void addSessionInteraction(String sessionText, Integer startHour, Integer startMin, Integer endHour,
      Integer endMin) {
    TextField sessionName = (TextField) parent.lookup("#sessionName");
    ComboBox<Integer> startH = (ComboBox<Integer>) parent.lookup("#startHour");
    ComboBox<Integer> startM = (ComboBox<Integer>) parent.lookup("#startMinute");
    ComboBox<Integer> endH = (ComboBox<Integer>) parent.lookup("#endHour");
    ComboBox<Integer> endM = (ComboBox<Integer>) parent.lookup("#endMinute");
    Button btnSave = (Button) parent.lookup("#btnSave");
    clickOn(sessionName).write(sessionText);
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      fail("Could not sleep, thread interupted.");
    }
    clickOn(startH);
    for (int i = 0; i <= startHour; i++) {
      type(KeyCode.DOWN);
    }
    type(KeyCode.ENTER);
    clickOn(startM);
    for (int i = 0; i <= startMin; i++) {
      type(KeyCode.DOWN);
    }
    type(KeyCode.ENTER);
    clickOn(endH);
    for (int i = 0; i <= endHour; i++) {
      type(KeyCode.DOWN);
    }
    type(KeyCode.ENTER);
    clickOn(endM);
    for (int i = 0; i <= endMin; i++) {
      type(KeyCode.DOWN);
    }
    type(KeyCode.ENTER);
    clickOn(btnSave);
  }
  // ------------------------- LOGOUT TESTS ---------------------------//
  /**
   * Logging the user out. Should make the login-screen visible, and the textfields should be empty.
   */
  @Test
  public void testLogoutFromUi() {
    signUpUserInteraction(SIGNUPUSER, VALIDPASSWORD);
    assertFalse(loginScreen.isVisible());
    Button btnLogout = (Button) parent.lookup("#btnLogout");
    clickOn(btnLogout);
    assertTrue(loginScreen.isVisible());
    assertNull(ControllerMediator.getInstance().getUserHandler().getTempUser());
    lblWrongLogin.getText().isBlank();
    sleep();
  }
  // ------------------------- ACCESS TESTS ---------------------------//
  /**
   * Test
   */
  @Test
  public void testGetUser() {
    User u = ControllerMediator.getInstance().getRegisterController().getTreningsAppAccess().getUser("user1", "password1");
    assertNotNull(u);
    assertEquals("user1", u.getName());
    assertEquals("password1", u.getPassword());
    assertNull(ControllerMediator.getInstance().getRegisterController().getTreningsAppAccess().getUser("nonExistingUser", "12345678"));
  }
  @Test
  public void getAddSession() {
    Session s = ControllerMediator.getInstance().getRegisterController().getTreningsAppAccess().getSession("user2", "Bakkeløp");
    System.out.println(s);
    assertNotNull(s);
    assertEquals("Bakkeløp", s.getTitle());
    assertNull(ControllerMediator.getInstance().getRegisterController().getTreningsAppAccess().getSession("user1", "Ikke-eksisterende-session"));
  }
}