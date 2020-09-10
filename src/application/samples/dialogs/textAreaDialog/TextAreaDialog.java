/**
 * 
 */
package application.samples.dialogs.textAreaDialog;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Spaecial dialog with a text area as main user input
 * 
 * @author Aldric Vitali Silvestre
 */
public class TextAreaDialog extends Dialog<String> {

	private final double WIDTH = 800;
	private final double HEIGHT = 500;

	/**
	 * Create a new dialog with text in text area
	 * 
	 * @param title   the title of the window
	 * @param content the text that appear above text area
	 * @param oldText the text to put in the text area beforehand
	 */
	public TextAreaDialog(String title, String content, String oldText) {
		super();
		
		Label contentLabel = new Label(content);
		contentLabel.setFont(new Font("Calibri", 17));
		
		TextArea textArea = new TextArea(oldText);
		Button submitButton = new Button("Modifier");
		Button cancelButton = new Button("Annuler");

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefHeight(HEIGHT);
		anchorPane.setPrefWidth(WIDTH);
		anchorPane.setMinHeight(HEIGHT);
		anchorPane.setMinWidth(WIDTH);
		anchorPane.setMaxHeight(HEIGHT);
		anchorPane.setMaxWidth(WIDTH);
		
		HBox hbox = new HBox(50);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().add(submitButton);
		hbox.getChildren().add(cancelButton);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(20));
		borderPane.setTop(contentLabel);
		borderPane.setCenter(textArea);
		borderPane.setBottom(hbox);

		Insets insets = new Insets(20, 10, 20, 10);
		BorderPane.setMargin(hbox, insets);
		BorderPane.setMargin(textArea, insets);
		BorderPane.setMargin(contentLabel, insets);
		
		
	}

	/**
	 * Create a new dialog without text in text area
	 * 
	 * @param title   the title of the window
	 * @param content the text that appear above text area
	 */
	public TextAreaDialog(String title, String content) {
		this(title, content, "");
	}

}
