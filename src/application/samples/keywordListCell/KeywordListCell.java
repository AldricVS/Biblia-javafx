package application.samples.keywordListCell;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Keyword display in a list of keywords
 * @author Aldric Vitali Silvestre
 */
public class KeywordListCell extends ListCell<String> {
	
	@FXML
	private Label keywordLabel;
	@FXML
	private Button removeButton;
	@FXML
	private AnchorPane mainPane;
	
	private FXMLLoader loader;

	@Override
	protected void updateItem(String keyword, boolean empty) {
		super.updateItem(keyword, empty);
		setStyle("-fx-background-color: transparent");
		if(empty || keyword == null) {
			
			setText(null);
			setGraphic(null);
			
		}else {
			
			if(loader == null) {
				loader = new FXMLLoader(getClass().getResource("KeywordListCell.fxml"));
				loader.setController(this);
				try {
					loader.load();
				}catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			//the only thing to add is the keyword string
			keywordLabel.setText(keyword);
			setText(null);
			setGraphic(mainPane);
		}
	}
	
	@FXML 
	public void removeKeyword(MouseEvent e) {

	}

}
