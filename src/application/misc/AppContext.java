package application.misc;

import application.MainPageController;
import javafx.stage.Stage;

public class AppContext {
	
	private MainPageController mainController;
	private Stage mainStage;
	
	private boolean isSaveNeeded;
	
	/**
	 * The only one instance of AppContext
	 */
	private static AppContext instance = new AppContext();
	
	/**
	 * Permits to have access to AppContext from anywhere
	 * @return the single instance of AppContext
	 */
	public static AppContext getInstance() {
		return instance;
	}

	/**
	 * @return the mainController
	 */
	public MainPageController getMainController() {
		return mainController;
	}

	/**
	 * @return the mainStage
	 */
	public Stage getMainStage() {
		return mainStage;
	}

	/**
	 * @param mainController the mainController to set
	 */
	public void setMainController(MainPageController mainController) {
		this.mainController = mainController;
	}

	/**
	 * @param mainStage the mainStage to set
	 */
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	/**
	 * @return the isSaveNeeded
	 */
	public boolean isSaveNeeded() {
		return isSaveNeeded;
	}

	/**
	 * @param isSaveNeeded the isSaveNeeded to set
	 */
	public void setSaveNeeded(boolean isSaveNeeded) {
		this.isSaveNeeded = isSaveNeeded;
	}
	
	
}
