package application.samples.dialogs.borrowDialog;

import java.io.IOException;
import java.util.HashMap;

import application.misc.AlertHelper;
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
	
	private boolean canClose;

	public BorrowDialog(String title, boolean isBorrowed, String borrower, String borrowDate) {

		super();
		Parent contentNode = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowDialog.fxml"));
			contentNode = loader.load();
			controller = loader.<BorrowDialogController>getController();
			controller.initData(isBorrowed, borrower, borrowDate);
		} catch (IOException e) {
			e.printStackTrace();
		}

		getDialogPane().setContent(contentNode);
		setTitle(title);

		// finally, we have the button management
		ButtonType buttonCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		ButtonType buttonSubmit = new ButtonType("Modifier", ButtonData.APPLY);
		getDialogPane().getButtonTypes().addAll(buttonSubmit, buttonCancel);

		this.setResultConverter(buttonType -> {
			canClose = true;
			if (buttonType == buttonSubmit) {
				if (AlertHelper.showYesNoAlert("Modifier l'état du livre ?",
						"Voulez-vous vraiment changer l'état d'emprunt du livre ?", null)) {

					//we want to know before is check box is check AND the two fileds are filled
					if(!controller.hasInconsitencies()) {
						AlertHelper.showErrorAlert("Pas normal !", "La case \"Emprunté\" à été cochée, mais tous les champs n'ont pas été remplis.", null);
						canClose = false;
					}
					
					HashMap<Integer, String> hashMap = controller.convertDataToHashMap();
					return hashMap;

				}
			}
			return null;
		});
		
		this.setOnCloseRequest(event -> {
			if(!canClose) {
				event.consume();
			}
		});
	}

}
