package treningsapp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TreningsApp extends Application {

  @Override
  public void start(final Stage primaryStage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass()
        .getResource("/treningsapp/ui/TreningsApp.fxml"));
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }

  /**
   * Run the application.
   *
   * @param args Makes it possible to use int and strings i backgroundmethods.
   */
  public static void main(final String[] args) {
    launch(args);
  }
}
