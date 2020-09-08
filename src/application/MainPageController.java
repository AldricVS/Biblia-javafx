package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import application.misc.AlertHelper;
import application.misc.DateStringConverter;
import application.misc.NoSelectableModel;
import application.samples.bookListCell.BookListViewCell;
import application.samples.keywordListCell.KeywordListCell;
import data.Book;
import data.BookList;
import data.Categories;
import data.Library;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.FocusModel;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import process.helpers.FileHelper;
import process.managers.LibraryManager;

/**
 * Controller for the main panel of the app.
 * 
 * @author Aldric Vitali Silvestre
 */
public class MainPageController implements Initializable {

	/* =======Attributes======= */

	// SEARCH PAGE
	@FXML
	private TextField searchBar;
	@FXML
	private Button searchButton;
	@FXML
	private ListView<Book> booksListView;
	@FXML
	private ScrollPane addBookScrollPane;

	private ObservableList<Book> bookObservaleList;
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

	public MainPageController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initCategoriesComboBox();

		// init books list view
		bookObservaleList = FXCollections.observableArrayList();
		booksListView.setSelectionModel(new NoSelectableModel<Book>());
		booksListView.setCellFactory(bookListView -> new BookListViewCell());

		keywordTextField.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				addKeywordToList();
			}
		});
		borrowDatePicker.setConverter(new DateStringConverter());
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

			bookList = LibraryManager.searchBooks(searchInput);
			if (bookList.getBookListSize() > 0) {
				bookObservaleList.clear();
				bookObservaleList.addAll(bookList.getBooks());
				booksListView.setItems(bookObservaleList);
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
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Euhhh...");
		alert.setHeaderText("Cette fonctionnalité n'est pas encore implémentée, donc...");
		alert.setContentText(null);
		alert.showAndWait();
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
		if(AlertHelper.showYesNoAlert("Ajouter ?", "Voulez-vous vraiment ajouter ce livre à la liste ?", "N'oubliez pas de sauvegarder ensuite")) {
			/*First, we gather all data and check if all is here*/
			String error = "";//empty string which will contain all errors
			String titleString = titleTextField.getText();
			if(titleString.isEmpty()) {
				error += "Le Champ \"titre\" n'est pas renseigné.\n";
			}
			String authorString = authorTextField.getText();
			if(authorString.isEmpty()) {
				error += "Le Champ \"auteur\" n'est pas renseigné.\n";
			}
			if(categoriesComboBox.getSelectionModel().isEmpty()) {
				error += "La catégorie n'est pas renseignée.\n";
			}
			Categories category = categoriesComboBox.getValue();
			if(categoriesComboBox.getSelectionModel().isEmpty()) {
				error += "La catégorie n'est pas renseignée.\n";
			}
			String keywords[] = getAllKeywords();
			if(keywords.length == 0) {
				error += "Un mot-clé doit au moins être ajouté.\n";
 			}
			String description = descriptionTextArea.getText();
			if(description.isEmpty()) {
				error += "Le Champ \"description\" n'est pas renseigné.\n";
			}
			String borrower = "", borrowDate = "";
			if(isBorrowPaneToggled()) {
				borrower = borrowerTextField.getText();
				LocalDate date = borrowDatePicker.getValue();
				if (borrower.isEmpty() || date == null) {
					error += "La case \"emprunté\" est cochée mais il n'y a pas les informations nécessaires.\n";
				}else {
					borrowDate = date.toString();
				}
			}
			
			/*If we don't have any error, we can create the book and store him*/
			if(error.isEmpty()) {
				Book book = new Book(titleString, authorString, category, keywords, description /*,isBorrowPaneToggled(), borrower, borrowDate*/);
				Library.getInstance().addBook(book);
				AlertHelper.showInformationAlert("Ajouté !", "Le livre a été ajouté avec succès", null);
			}else {
				AlertHelper.showErrorAlert("Champs manquants", "Certaines informations importantes sont manquantes.", error);
			}
			
		}
	}

	private String[] getAllKeywords() {
		ObservableList<Node> buttons = keywordsFlowPane.getChildren();
		int numberKeywords = buttons.size();
		String keywords[] = new String[numberKeywords];
		for(int i = 0; i < numberKeywords; i++){
			keywords[i] = ((Button)buttons.get(i)).getText();
		}
		return keywords;
	}

	// MENU BAR
	@FXML
	public boolean saveLibrary(ActionEvent e) {
		try {
			LibraryManager.saveLibrary();
			AlertHelper.showInformationAlert("Sauvegarde réussie", "La bibliothèque a été sauvegardée avec succès !", null);
			return true;
		} catch (IOException e1) {
			AlertHelper.showErrorAlert("Errue !", "Une erreur a eu lieu durant la sauvegarde.", "Raison : " + e1);
			return false;
		}
	}

	@FXML
	public void quitApp(ActionEvent e) {
		
		int answer = AlertHelper.showYesNoCancelAlert("Sauvegarder ?", "Voulez-vous sauvegarder avant de quiter ?", null);
		
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

	/* ======Misc Private Methods====== */

	private void addKeywordButton(String keyword) {
		Button button = new Button(keyword);
		//add a linstener to this specific button to remove it
		button.setOnMouseClicked(event -> {
			if(AlertHelper.showYesNoAlert("Supprimer le mot-clé ?", "Voulez-vous vraiment supprimer le mot clé ?", null)) {
				keywordsFlowPane.getChildren().remove(button);
			}
		});
		button.getStyleClass().add("keyword-button");
		keywordsFlowPane.getChildren().add(button);
	}
	
	private boolean isBorrowPaneToggled() {
		return borrowCheckBox.isSelected();
	}

}
