package application;
	
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import process.managers.LibraryManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setMinWidth(848f);
			primaryStage.setMinHeight(480f);
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageSample.fxml"));
			
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Bibila");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
