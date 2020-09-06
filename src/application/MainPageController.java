package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.misc.NoSelectableModel;
import application.samples.bookListCell.BookListViewCell;
import application.samples.keywordListCell.KeywordListCell;
import data.Book;
import data.BookList;
import data.Categories;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.FocusModel;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import process.helpers.FileHelper;
import process.managers.LibraryManager;

/**
 * Controller for the main panel of the app.
 * @author Aldric Vitali Silvestre
 */
public class MainPageController implements Initializable {
	
	/*=======Attributes=======*/
	
	//SEARCH PAGE
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
	
	//ADD PAGE
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
	private ListView<String> keywordsListView;
	private ObservableList<String> keywordsObservableList;
	
	/*=======Initialization=======*/

	public MainPageController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initCategoriesComboBox();
		
		//init books list view
		bookObservaleList = FXCollections.observableArrayList();
		booksListView.setSelectionModel(new NoSelectableModel<Book>());
		booksListView.setCellFactory(bookListView -> new BookListViewCell());
		
		//init keywords list view
		keywordsObservableList = FXCollections.observableArrayList();
		keywordsListView.setSelectionModel(new NoSelectableModel<String>());
		keywordsListView.setCellFactory(keywordCell -> new KeywordListCell());
		keywordTextField.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER)) {
				addKeywordToList();
			}
		});
	}

	private void initCategoriesComboBox() {
		Categories categories[] = Categories.values();
		for(int i = 0; i < categories.length; i++){
			categoriesComboBox.getItems().add(categories[i]);
		}
	}
	
	/*========Methods listeners=======*/
	//SEARCH PAGE
	@FXML
	public void startSearchFromField(KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
			searchBooks();
		}
	}
	
	@FXML
	public void startSearchFromButton(MouseEvent e) {
		searchBooks();
	}

	private void searchBooks() {
		//get the input text with non needed spaces removed
		String searchInput = searchBar.getText().trim().replaceAll(" +", " ");
		if(!searchInput.isEmpty()) {
			
			bookList = LibraryManager.searchBooks(searchInput);
			if(bookList.getBookListSize() > 0) {
				bookObservaleList.clear();
				bookObservaleList.addAll(bookList.getBooks());
				booksListView.setItems(bookObservaleList);
			}else {
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
	
	//ADD PAGE
	@FXML
	public void addKeywordToListButton(MouseEvent e) {
		addKeywordToList();
	}
	private void addKeywordToList() {
		String text = keywordTextField.getText();
		if(!text.isEmpty()) {
			keywordsObservableList.add(text);
			keywordsListView.setItems(keywordsObservableList);
			keywordTextField.setText("");
		}
	}
	
	//MENU BAR
	@FXML
	public boolean saveLibrary(ActionEvent e) {
		try {
			LibraryManager.saveLibrary();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Savegarde reussie !");
			alert.setHeaderText("Bibliothèque sauvegardée avec succès.");
			alert.setContentText(null);
			alert.showAndWait();
			return true;
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur de sauvegarde !");
			alert.setHeaderText("Une erreur a eu lieu durant la sauvegarde.");
			alert.setContentText("Raison :" + e1.getMessage());
			alert.showAndWait();
			return false;
		}
	}
	
	@FXML
	public void quitApp(ActionEvent e) {
		
		ButtonType buttonTypeYes = new ButtonType("Oui");
		ButtonType buttonTypeNo = new ButtonType("Non");
		ButtonType buttonTypeCancel = new ButtonType("Annuler");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quitter ?");
		alert.setHeaderText("Voulez-vous sauvegarder avant de quitter ?");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonTypeYes) {
			if(saveLibrary(null)) {
				Platform.exit();
				System.exit(0);
			}
		}else if(result.get() == buttonTypeNo) {
			Platform.exit();
			System.exit(0);
		}
	}

}
