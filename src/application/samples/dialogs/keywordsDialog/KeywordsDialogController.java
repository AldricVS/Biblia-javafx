package application.samples.dialogs.keywordsDialog;

import application.misc.AlertHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

/**
 * Controller for the keywords list dialog
 * @author Aldric Vitali Silvestre
 */
public class KeywordsDialogController {
	@FXML
	private TextField keywordTextField;
	@FXML
	private Button addKeywordButton;
	@FXML
	private FlowPane keywordsFlowPane;

	@FXML
	public void addKeywordIfEnterPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			addKeyword();
		}
	}

	@FXML
	public void addKeyword() {
		String newKeyword = keywordTextField.getText().trim();
		if (!newKeyword.isEmpty()) {
			//add keyword button to keyword flow pane
			Button button = createKeywordButton(keywordTextField.getText());
			keywordsFlowPane.getChildren().add(button);
			//and restore text field
			keywordTextField.setText("");
		}
	}

	private Button createKeywordButton(String content) {
		// we have to create a button, add a style class for beauty and a listener if
		// user click on it that ask if wants to remove
		Button button = new Button(content);
		button.getStyleClass().add("keyword-button");
		button.setOnAction(event -> {
			if (AlertHelper.showYesNoAlert("Supprimer le mot-clé ?",
					"Voulez-vous vraiment supprimer le mot clé \"" + button.getText() + "\" de la liste ?", null)) {
				keywordsFlowPane.getChildren().remove(button);
			}
		});
		return button;
	}

	/**
	 * Init the keywords flow pane in the dialog.<p>
	 * The keywords string is presented like this : 
	 * <pre>keyword1;keyword2;...</pre>
	 * @param keywords the string containing keywords to add to flowPane
	 */
	public void initKeywords(String keywords) {
		if(keywords == null)
			return;
		String keywordsArray[] = keywords.split(";");
		for(int i = 0; i < keywordsArray.length; i++) {
			Button button = createKeywordButton(keywordsArray[i]);
			keywordsFlowPane.getChildren().add(button);
		}
	}
	
	/**
	 * get keywords list from the keywords in the dialog flowPane.
	 * @return the keywords list, as a single string
	 */
	public String getKeywordsAsSingleString() {
		ObservableList<Node> buttons = keywordsFlowPane.getChildren();
		StringBuilder sb = new StringBuilder();
		for(Node node : buttons){
			//all nodes in keywords obeservable list are buttons
			Button button = (Button)node;
			sb.append(button.getText());
			sb.append(";");
		}
		return sb.toString().substring(0, sb.length() - 1);
	}
}
