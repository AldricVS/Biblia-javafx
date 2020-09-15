package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.misc.AlertHelper;
import application.misc.AppContext;
import application.misc.DateStringConverter;
import application.misc.NoSelectableModel;
import application.samples.bookListCell.BookListViewCellController;
import application.samples.bookPage.BookPageController;
import data.Book;
import data.BookList;
import data.Categories;
import data.Library;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import process.managers.LibraryManager;

/**
 * Controller for the main panel of the app.
 * <p>
 * You must call {@link MainPageController#initStage} in order to add all
 * features, such as close window verification.
 * 
 * @author Aldric Vitali Silvestre
 */
public class MainPageController implements Initializable {

	/* =======Attributes======= */
	// ALL PAGES
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab addBookTabPane;
	@FXML
	private Tab searchTabPane;

	// SEARCH PAGE
	@FXML
	private TextField searchBar;
	@FXML
	private Button searchButton;
	@FXML
	private ListView<Book> booksListView;
	@FXML
	private ScrollPane addBookScrollPane;

	private ObservableList<Book> bookObservableList;
	private BookList bookList;

	// ADD PAGE
	@FXML
	private TextField titleTextField;
	@FXML
	private TextField authorTextField;
	@FXML
	private ComboBox<Categories> categoriesComboBox;
	@FXML
	private TextField keywordTextField;
	@FXML
	private Button addKeywordButton;
	@FXML
	private FlowPane keywordsFlowPane;
	@FXML
	private TextArea descriptionTextArea;
	@FXML
	private CheckBox borrowCheckBox;
	@FXML
	private BorderPane borrowBorderPane;
	@FXML
	private TextField borrowerTextField;
	@FXML
	private DatePicker borrowDatePicker;
	@FXML
	private Button addBookButton;

	/* =======Initialization======= */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// set the value for appContext
		AppContext.getInstance().setMainController(this);

		// listener to remove all book searched if user switch to the add book tab
		tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
			if (oldTab == searchTabPane) {
				clearSearchPane();
			}
		});

		initCategoriesComboBox();
		// init books list view
		bookObservableList = FXCollections.observableArrayList();
		booksListView.setSelectionModel(new NoSelectableModel<Book>());
		booksListView.setCellFactory(bookListView -> new BookListViewCellController());

		keywordTextField.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				addKeywordToList();
			}
		});
		borrowDatePicker.setConverter(new DateStringConverter());
	}

	/**
	 * link the stage with the controller, in order to use full potential of window
	 * event handling
	 * 
	 * @param stage the main stage of the application
	 */
	public void initStage(Stage stage) {
		stage.setOnCloseRequest(windowEvent -> {
			if (AppContext.getInstance().isSaveNeeded()) {
				int answer = AlertHelper.showYesNoCancelAlert("Sauvegarder ?",
						"Voulez-vous sauvegarder avant de quiter ?",
						"Cela serait dommage de perdre toutes les modifications.");

				if (answer == AlertHelper.CHOICE_YES) {
					if (saveLibrary(null)) {
						Platform.exit();
						System.exit(0);
					}
				} else if (answer == AlertHelper.CHOICE_NO) {
					Platform.exit();
					System.exit(0);
				} else {
					// here, user click on "cancel" button, so we have to keep window open
					windowEvent.consume();
				}
			}
		});

	}

	private void initCategoriesComboBox() {
		Categories categories[] = Categories.values();
		for (int i = 0; i < categories.length; i++) {
			categoriesComboBox.getItems().add(categories[i]);
		}
	}

	/* ========Methods listeners======= */
	// SEARCH PAGE
	@FXML
	public void startSearchFromField(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			searchBooks();
		}
	}

	@FXML
	public void startSearchFromButton(MouseEvent e) {
		searchBooks();
	}

	private void searchBooks() {
		// get the input text with non needed spaces removed
		String searchInput = searchBar.getText().trim().replaceAll(" +", " ");
		if (!searchInput.isEmpty()) {

			BookList bookList = LibraryManager.searchBooks(searchInput);
			if (bookList.getSize() > 0) {
				refreshSearch(bookList.getBooks());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Pas de livres !");
				alert.setHeaderText("Aucun livre n'a été trouvé pour cette recherche.");
				alert.setContentText(null);
				alert.showAndWait();
			}
		}
	}


	@FXML
	public void listBorrowedBooks(MouseEvent e) {
		bookList = LibraryManager.getAllBorrowedBooks();
		if (bookList.isEmpty()) {
			AlertHelper.showErrorAlert("Pas de livres empruntés", "Il n'y a aucun livre emprunté dans la bibliothèque.",
					"Pour l'instant...");
		} else {
			bookObservableList.clear();
			bookObservableList.addAll(bookList.getBooks());
			booksListView.setItems(bookObservableList);
		}
	}
	
	@FXML
	public void openTitleInitialSearchDialog(ActionEvent e) {
		
		//open comboBox with all letters for the user
		char answer = AlertHelper.showAlphabetSelectionDialog("Recherche par initiale", "Choisissez une lettre dans la liste", null);
		if(answer != Character.MIN_VALUE) {
			BookList bookList = LibraryManager.searchBooksByTitleInitial(answer);
			if(bookList.isEmpty()) {
				AlertHelper.showErrorAlert("Pas de livre trouvé", "Aucun livre avec l'initiale " + answer + " n'a été trouvé.", null);
			}else {
				refreshSearch(bookList.getBooks());	
			}
		}
	}

	// ADD PAGE
	@FXML
	public void addKeywordToListButton(MouseEvent e) {
		addKeywordToList();
	}

	private void addKeywordToList() {
		String text = keywordTextField.getText();
		if (!text.isEmpty()) {
			addKeywordButton(text);
			keywordTextField.setText("");
		}
	}

	@FXML
	public void toggleBorrowPane(MouseEvent e) {
		borrowBorderPane.setDisable(!isBorrowPaneToggled());
	}

	@FXML
	public void addNewBook(MouseEvent e) {
		if (AlertHelper.showYesNoAlert("Ajouter ?", "Voulez-vous vraiment ajouter ce livre à la liste ?",
				"N'oubliez pas de sauvegarder ensuite")) {
			/* First, we gather all data and check if all is here */
			String error = "";// empty string which will contain all errors
			String titleString = titleTextField.getText();
			if (titleString.isEmpty()) {
				error += "Le Champ \"titre\" n'est pas renseigné.\n";
			}
			String authorString = authorTextField.getText();
			if (authorString.isEmpty()) {
				error += "Le Champ \"auteur\" n'est pas renseigné.\n";
			}
			if (categoriesComboBox.getSelectionModel().isEmpty()) {
				error += "La catégorie n'est pas renseignée.\n";
			}
			Categories category = categoriesComboBox.getValue();
			if (categoriesComboBox.getSelectionModel().isEmpty()) {
				error += "La catégorie n'est pas renseignée.\n";
			}
			String keywords[] = getAllKeywords();
			if (keywords.length == 0) {
				error += "Un mot-clé doit au moins être ajouté.\n";
			}
			String description = descriptionTextArea.getText();
			if (description.isEmpty()) {
				error += "Le Champ \"description\" n'est pas renseigné.\n";
			}
			String borrower = "", borrowDate = "";
			boolean isBookBorrowed = isBorrowPaneToggled();
			if (isBookBorrowed) {
				borrower = borrowerTextField.getText();
				LocalDate date = borrowDatePicker.getValue();
				if (borrower.isEmpty() || date == null) {
					error += "La case \"emprunté\" est cochée mais il n'y a pas les informations nécessaires.\n";
				} else {
					StringConverter<LocalDate> converter = borrowDatePicker.getConverter();
					LocalDate value = borrowDatePicker.getValue();
					borrowDate = converter.toString(value);
				}
			}

			/* If we don't have any error, we can create the book and store him */
			if (error.isEmpty()) {
				Book book = new Book(titleString, authorString, category, keywords, description, isBookBorrowed,
						borrower, borrowDate);
				Library.getInstance().addBook(book);
				AlertHelper.showInformationAlert("Ajouté !", "Le livre a été ajouté avec succès", null);
				
				AppContext.getInstance().setSaveNeeded(true);
				
				// we also want to empty all fields
				clearAllSearchFields();
			} else {
				AlertHelper.showErrorAlert("Champs manquants", "Certaines informations importantes sont manquantes.",
						error);
			}

		}
	}

	private String[] getAllKeywords() {
		ObservableList<Node> buttons = keywordsFlowPane.getChildren();
		int numberKeywords = buttons.size();
		String keywords[] = new String[numberKeywords];
		for (int i = 0; i < numberKeywords; i++) {
			keywords[i] = ((Button) buttons.get(i)).getText();
		}
		return keywords;
	}

	// MENU BAR
	@FXML
	public boolean saveLibrary(ActionEvent e) {
		AppContext.getInstance().setSaveNeeded(false);
		try {
			LibraryManager.saveLibrary();
			AlertHelper.showInformationAlert("Sauvegarde réussie", "La bibliothèque a été sauvegardée avec succès !",
					null);
			return true;
		} catch (IOException e1) {
			AlertHelper.showErrorAlert("Errue !", "Une erreur a eu lieu durant la sauvegarde.", "Raison : " + e1);
			return false;
		}
	}

	@FXML
	public void quitApp(ActionEvent e) {
		if (AppContext.getInstance().isSaveNeeded()) {
			int answer = AlertHelper.showYesNoCancelAlert("Sauvegarder ?", "Voulez-vous sauvegarder avant de quiter ?",
					null);

			if (answer == AlertHelper.CHOICE_YES) {
				if (saveLibrary(null)) {
					Platform.exit();
					System.exit(0);
				}
			} else if (answer == AlertHelper.CHOICE_NO) {
				Platform.exit();
				System.exit(0);
			}
		}
	}
	
	@FXML
	public void openAboutDialog(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("samples/dialogs/aboutDialog/aboutDialog.fxml"));
		Parent parent;
		try {
			parent = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("À propos");
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void openStatsDialog(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("samples/dialogs/statsDialog/statsDialog.fxml"));
		Parent parent;
		try {
			parent = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Statistiques");
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/* =======Misc Public methods======= */

	/**
	 * Permits to refresh the list view. Useful when doing modifications in other
	 * pages.
	 */
	public void refreshListView() {
		booksListView.refresh();
	}

	/**
	 * Remove a book from the list view (used by BookPageController when deleting
	 * book)
	 * 
	 * @param book the book to remove from the list (if exists)
	 */
	public void removeBookFromListView(Book book) {
		booksListView.getItems().remove(book);
	}

	/* =======Misc Private Methods====== */
	
	private void refreshSearch(ArrayList<Book> books) {
		bookObservableList.clear();
		bookObservableList.addAll(books);
		booksListView.setItems(bookObservableList);
	}

	private void clearSearchPane() {
		// remove all books from listview
		booksListView.setItems(null);
		bookObservableList.clear();
		// and clear searchbar
		searchBar.setText("");
	}

	private void addKeywordButton(String keyword) {
		Button button = new Button(keyword);
		// add a linstener to this specific button to remove it
		button.setOnMouseClicked(event -> {
			if (AlertHelper.showYesNoAlert("Supprimer le mot-clé ?", "Voulez-vous vraiment supprimer le mot clé ?",
					null)) {
				keywordsFlowPane.getChildren().remove(button);
			}
		});
		button.getStyleClass().add("keyword-button");
		keywordsFlowPane.getChildren().add(button);
	}

	private boolean isBorrowPaneToggled() {
		return borrowCheckBox.isSelected();
	}

	private void clearAllSearchFields() {
		String emptyString = "";
		titleTextField.setText(emptyString);
		authorTextField.setText(emptyString);
		categoriesComboBox.setValue(null);
		keywordTextField.setText(emptyString);
		descriptionTextArea.setText(emptyString);
		borrowCheckBox.setSelected(false);
		borrowerTextField.setText(emptyString);
		borrowDatePicker.setValue(null);
		keywordsFlowPane.getChildren().clear();
	}

}
