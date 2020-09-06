/**
 * 
 */
package application.misc;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Class used to create alerts easily
 * @author Aldric Vitali Silvestre
 */
public class AlertHelper {
	
	/**
	 * Code returned by the confirmation alert box if user chose yes 
	 */
	public static final int CHOICE_YES = 0;
	/**
	 * Code returned by the confirmation alert box if user chose no 
	 */
	public static final int CHOICE_NO = 1;
	/**
	 * Code returned by the confirmation alert box if user chose cancel
	 */
	public static final int CHOICE_CANCEL = 2;
	
	private static final ButtonType BUTTON_TYPE_OUI = new ButtonType("Oui");
	private static final ButtonType BUTTON_TYPE_NON = new ButtonType("Non");
	private static final ButtonType BUTTON_TYPE_ANNULER = new ButtonType("Annuler");

	/**
	 * Show a simple inforation message
	 * @param title the title of the pop-up window
	 * @param header the header of the pop-up window (it will be dispalyed bigger than content) 
	 * @param content the content of the window. 
	 */
	public static void showInformationAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	/**
	 * Show an error message
	 * @param title the title of the pop-up window
	 * @param header the header of the pop-up window (it will be dispalyed bigger than content) 
	 * @param content the content of the window. 
	 */
	public static void showErrorAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	/**
	 * Show an alert which requires the user to choose an answer among "oui", "non" or "anuler"
	 * @param title the title of the pop-up window
	 * @param header the header of the pop-up window (it will be dispalyed bigger than content) 
	 * @param content the content of the window. 
	 * @return an integer which represents the answer of the user.<p>
	 * It can take 3 values :
	 * <ul>
	 * 	<li>CHOICE_YES</li>
	 * 	<li>CHOICE_NO</li>
	 * 	<li>CHOICE_CANCEL</li>
	 * </ul>
	 */
	public static int showYesNoCancelAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(BUTTON_TYPE_OUI, BUTTON_TYPE_NON, BUTTON_TYPE_ANNULER);
		
		Optional<ButtonType> answer = alert.showAndWait();
		
		if(answer.get() == BUTTON_TYPE_OUI) {
			return CHOICE_YES;
		}else if (answer.get() == BUTTON_TYPE_NON) {
			return CHOICE_NO;
		}else {
			return CHOICE_CANCEL;
		}
		
	}
	
	/**
	 * Show an alert which requires the user to choose an answer among "oui" or "non"
	 * @param title the title of the pop-up window
	 * @param header the header of the pop-up window (it will be dispalyed bigger than content) 
	 * @param content the content of the window. 
	 * @return true if the user choose to click on the positive button, false else.
	 */
	public static boolean showYesNoAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(BUTTON_TYPE_OUI, BUTTON_TYPE_NON);
		
		Optional<ButtonType> answer = alert.showAndWait();
		
		return answer.get() == BUTTON_TYPE_OUI;
	}
	
}
