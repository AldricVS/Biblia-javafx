package application.samples.dialogs.borrowDialog;

import java.io.IOException;
import java.util.HashMap;

import application.samples.dialogs.keywordsDialog.KeywordsDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class BorrowDialog extends Dialog<HashMap<Integer, String>> {

	public static final Integer FIELD_IS_BORROWED = 0;
	public static final Integer FIELD_BORROWER = 1;
	public static final Integer FIELD_BORROW_DATE = 2;
	
	private BorrowDialogController controller;

	public BorrowDialog(String title, boolean isBorrowed, String borrower, String borrowDate) {
		
		super();
		Parent contentNode;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowDialog.fxml"));
			contentNode = loader.load();
			controller = loader.<BorrowDialogController>getController();
			controller.initData(isBorrowed, borrower, borrowDate);
		} catch (IOException e) {
			throw new RuntimeException("Error while initilizing borrow Dialog\n" + e);
		}
	
		getDialogPane().setContent(contentNode);
		setTitle(title);
		
		// finally, we have the button management
		ButtonType buttonCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		ButtonType buttonSubmit = new ButtonType("Modifier", ButtonData.APPLY);
		getDialogPane().getButtonTypes().addAll(buttonSubmit, buttonCancel);
		
		this.setResultConverter(buttonType -> {
			if(buttonType == buttonSubmit) {
				HashMap<Integer, String> hashMap = controller.convertDataToHashMap();
				return hashMap;
			}
			return null;
		});
	}

}
