/**
 * 
 */
package application.samples.dialogs.textAreaDialog;

import application.misc.AlertHelper;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
		textArea.setCache(false);
		
		//create the main panel of the pop-up and set all size values
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefHeight(HEIGHT);
		anchorPane.setPrefWidth(WIDTH);
		anchorPane.setMinHeight(HEIGHT);
		anchorPane.setMinWidth(WIDTH);
		anchorPane.setMaxHeight(HEIGHT);
		anchorPane.setMaxWidth(WIDTH);

		//inside this we have a border pane so the content label is at top and
		//text area get the rest
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(20));
		borderPane.setTop(contentLabel);
		borderPane.setCenter(textArea);

		//we define all margins and add border pane to anchor
		Insets insets = new Insets(20, 10, 20, 10);
		BorderPane.setMargin(textArea, insets);
		BorderPane.setMargin(contentLabel, insets);
		anchorPane.getChildren().add(borderPane);
		AnchorPane.setBottomAnchor(borderPane, 0d);
		AnchorPane.setTopAnchor(borderPane, 0d);
		AnchorPane.setRightAnchor(borderPane, 0d);
		AnchorPane.setLeftAnchor(borderPane, 0d);
		
		getDialogPane().setContent(anchorPane);
		setTitle(title);
		
		getDialogPane().setStyle("-fx-font-size:18.0px;-fx-font-family:\"Calibri\"");
		
		//finally, we have the button management
		ButtonType buttonCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		ButtonType buttonSubmit = new ButtonType("Modifier", ButtonData.APPLY);
		getDialogPane().getButtonTypes().addAll(buttonSubmit,buttonCancel);
		
		//and this is the converter buttonType => string (the one in textArea)
		setResultConverter(buttonType -> {
			if(buttonType.getButtonData().equals(buttonSubmit.getButtonData())) {
				//little warning for user
				if(AlertHelper.showYesNoAlert("Modifier", "Voulez-vous vraiment modifier la description ?", null)) {
					String textInTextArea = textArea.getText();
					if(!textInTextArea.trim().isEmpty()) {
						return textInTextArea;
					}
				}
			}
			return null;
		});
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
