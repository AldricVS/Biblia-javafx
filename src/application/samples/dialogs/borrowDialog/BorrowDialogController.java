package application.samples.dialogs.borrowDialog;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

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
		if(isBorrow) {
			borrowDatePicker.setConverter(new DateStringConverter());
			//set the date from date string
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(borrowDate, formatter);
			borrowDatePicker.setValue(localDate);
		}else {
			borrowDatePicker.setValue(LocalDate.now());
		}
	}

	/**
	 * Put all data needed in an hashmap in order to retrieve data after.<p>
	 * The hashMap is formed with 3 values : 
	 * <ul>
	 * <li>FIELD_IS_BORROWED : contains the string "1" if book is borrowed, "0" else</li>
	 * <li>FIELD_BORROWER : get the text contained in the text field (where the name of the borrower is put)</li>
	 * <li>FIELD_BORROW_DATE : the date where the borrow was done, printed in the format dd/MM/yyyy</li>
	 * </ul>
	 * @return the hashMap containg those 3 values
	 */
	public HashMap<Integer, String> convertDataToHashMap() {
		HashMap<Integer, String> hm = new HashMap<>();
		boolean isSelected = checkBox.isSelected();
		
		if(isSelected) {
			hm.put(BorrowDialog.FIELD_IS_BORROWED, "1");
			hm.put(BorrowDialog.FIELD_BORROWER, borrowerTextField.getText());
			StringConverter<LocalDate> converter = borrowDatePicker.getConverter();
			LocalDate value = borrowDatePicker.getValue();
			hm.put(BorrowDialog.FIELD_BORROW_DATE, converter.toString(value));
		}else {
			hm.put(BorrowDialog.FIELD_IS_BORROWED, "0");
			hm.put(BorrowDialog.FIELD_BORROWER, "");
			hm.put(BorrowDialog.FIELD_BORROW_DATE, "");
		}
		
		return hm;
	}

	/**
	 * Check if something is wrong with the configuration of the dialog.<p>
	 * If checkBox is checked, date and borrower fields must be empty.
	 * @return
	 */
	public boolean hasInconsitencies() {
		if(checkBox.isSelected()) {
			return !borrowerTextField.getText().trim().isEmpty() && borrowDatePicker.getValue() != null;
		}
		return true;
	}
}
