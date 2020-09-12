package application.samples.dialogs.borrowDialog;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import application.misc.DateStringConverter;
import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;

public class BorrowDialogController {
	@FXML
	private CheckBox checkBox;
	@FXML
	private TextField borrowerTextField;
	@FXML
	private DatePicker borrowDatePicker;
	@FXML
	private VBox borrowPane;

	// Event Listener on CheckBox[#checkBox].onAction
	@FXML
	public void toggleBorrowPane(ActionEvent event) {
		boolean isSelected = checkBox.isSelected();
		borrowPane.setDisable(!isSelected);
	}

	public void initData(boolean isBorrow, String borrower, String borrowDate) {
		borrowPane.setDisable(!isBorrow);
		borrowerTextField.setText(borrower);
		borrowDatePicker.setConverter(new DateStringConverter());
		//set the date from date string
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
		LocalDate localDate = LocalDate.parse(borrowDate, formatter);
		borrowDatePicker.setValue(localDate);
	}

	public HashMap<Integer, String> convertDataToHashMap() {
		HashMap<Integer, String> hm = new HashMap<>();
		hm.put(BorrowDialog.FIELD_IS_BORROWED, checkBox.isSelected() ? "0" : "1");
		hm.put(BorrowDialog.FIELD_BORROWER, borrowerTextField.getText());
		hm.put(BorrowDialog.FIELD_BORROW_DATE, borrowDatePicker.getValue().toString());
		return hm;
	}
}
