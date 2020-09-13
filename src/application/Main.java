package application;

import java.io.FileInputStream;

import application.misc.AppContext;
import data.Library;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Library.getInstance();
			primaryStage.setMinWidth(848f);
			primaryStage.setMinHeight(480f);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageSample.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			AppContext.getInstance().setMainStage(primaryStage);
			primaryStage.setTitle("Bibila");
			primaryStage.setScene(scene);
			MainPageController controller = loader.getController();
			controller.initStage(primaryStage);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(new FileInputStream("@../../Images/mainIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
