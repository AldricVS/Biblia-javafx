/**
 * 
 */
package application.samples.dialogs.keywordsDialog;

import java.io.IOException;

import application.misc.AlertHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * Special dialog with a keywords add system inside
 * 
 * @author Aldric Vitali Silvestre
 */
public class KeywordsDialog extends Dialog<String> {

	@FXML
	private TextField keywordTextField;
	@FXML
	private Button addKeywordButton;
	@FXML
	private FlowPane keywordsFlowPane;

	private KeywordsDialogController controller;

	/**
	 * Create a new dialog with text in text area
	 * 
	 * @param title       the title of the window
	 * @param oldKeywords the keywords to replace eventually
	 */
	public KeywordsDialog(String title, String oldKeywords) {
		super();
		Parent contentNode;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("KeywordsDialog.fxml"));
			contentNode = loader.load();
			controller = loader.<KeywordsDialogController>getController();
			controller.initKeywords(oldKeywords);
		} catch (IOException e) {
			throw new RuntimeException("Error while initilizing keywordsDialog\n" + e);
		}

		getDialogPane().setContent(contentNode);
		setTitle(title);

		// finally, we have the button management
		ButtonType buttonCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		ButtonType buttonSubmit = new ButtonType("Modifier", ButtonData.APPLY);
		getDialogPane().getButtonTypes().addAll(buttonSubmit, buttonCancel);

		// set the result of the dialog
		setResultConverter(buttonType -> {
			if (buttonType == buttonSubmit) {
				//little warning before doing any modification
				if (AlertHelper.showYesNoAlert("Modifier les mots-clés ?",
						"Voulez-vous vraiment modifier les mots-clés de ce livre ?",
						"Ce choix n'aura de conséquances qu'après avoir sauvegardé toutes les moifications du livre.")) {
					return controller.getKeywordsAsSingleString();
				}
			}
			//if user decided to not save or clicked on cancel
			return null;
		});
	}

	/**
	 * Create a new dialog without text in text area
	 * 
	 * @param title the title of the window
	 */
	public KeywordsDialog(String title) {
		this(title, null);
	}

}
