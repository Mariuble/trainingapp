package treningsapp.ui;

import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import treningsapp.core.User;


public class RegisterController {
  // userHandler contains user handling.

  // FXML elements loginscreen
  @FXML
  protected Pane loginScreen;
  @FXML
  protected TextField txtUsername;
  @FXML
  protected TextField txtPassword;
  @FXML
  protected Button btnLogin;
  @FXML
  protected Label lblWrongLogin;



  private TreningsAppAccess treningsAppAccess;


  /**
   * First method to run.
   * 
   * 
   */
  public void initialize() {
    try {
      this.treningsAppAccess = new TreningsAppAccess(new URI("http://localhost:8080/treningsapp/"));
    } catch (URISyntaxException e) {
      System.out.println("Server....." + e);
      
    }
    ControllerMediator.getInstance().registerController(this);
  }

  public TreningsAppAccess getTreningsAppAccess() {
    return this.treningsAppAccess;
  }

  /**
   * Gathers information from the user interface, and use this to attempt login.
   */
  @FXML
  public void loginFromUi() {
    String username = txtUsername.getText();
    String password = txtPassword.getText();
    //User tempUser = treningsAppAccess.getUser(username, password);
    if (treningsAppAccess.getUserHandler().login(username, password)) {
      //if (treningsAppAccess.userExists(username, password)) {
      txtUsername.setText("");
      txtPassword.setText("");
      lblWrongLogin.setText("");
      loginScreen.setVisible(false);
      ControllerMediator.getInstance().getAddSessionController().updateUserInfo();
    } else {
      lblWrongLogin.setText("Wrong username or password... try again");
    }
  }

  /**
   * Gather information from the UI, and attempt to sign up a new user.
   */
  @FXML
  public void signUpFromUi() {
    String username = txtUsername.getText();
    String password = txtPassword.getText();
    String feedbackMessage = treningsAppAccess.getUserHandler().verifyNewUser(username, password);
    if (feedbackMessage.isEmpty()) {
      User user = new User(username, password);
      treningsAppAccess.putUser(user);
      //treningsAppAccess.getUserHandler().addUser(user);
      loginFromUi();
    } else {
      updateLoginFeedback(feedbackMessage);
    }
  }

  @FXML
  public void updateLoginFeedback(String str) {
    lblWrongLogin.setText(str);
  }

  @FXML
  public void printUserHandler() {
    System.out.println(this.treningsAppAccess.getUserHandler().toString());
  }
}
