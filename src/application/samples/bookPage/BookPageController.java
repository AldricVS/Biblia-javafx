package application.samples.bookPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringJoiner;

import application.misc.AlertHelper;
import application.misc.AppContext;
import application.samples.dialogs.borrowDialog.BorrowDialog;
import application.samples.dialogs.keywordsDialog.KeywordsDialog;
import application.samples.dialogs.textAreaDialog.TextAreaDialog;
import data.Book;
import data.Categories;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import process.factory.BookFactory;
import process.managers.LibraryManager;

/**
 * Controller for displaying a book informations's window.
 * <p>
 * To display this, you need to load the FXML resource, and then call
 * {@link BookPageController#initData(Book)} in order to make it work.
 * 
 * @author Aldric Vitali Silvestre
 * @see BookPageController#initData(Book)
 */
public class BookPageController implements Initializable {
	/* =======Page components======= */
	@FXML
	private Button modifyDescriptionButton;
	@FXML
	private TextArea descriptionTextArea;
	@FXML
	private Button modifyKeywordsButton;
	@FXML
	private TextArea keywordsTextArea;
	@FXML
	private Label titleLabel;
	@FXML
	private Button modifyTitleButton;
	@FXML
	private Label authorLabel;
	@FXML
	private Button modifyAuthorButton;
	@FXML
	private Label categoryLabel;
	@FXML
	private Button modifyCategoryButton;
	@FXML
	private Button saveModificationsButton;
	@FXML
	private Button deleteBookButton;
	@FXML
	private Label borrowLabel;
	@FXML
	private Button modifyBorrowButton;

	private Stage stage;

	/* =======Attributes======= */
	private Book originalBook;

	private Categories category;

	private boolean isBorrowed;
	private String borrower;
	private String borrowDate;

	private final String STRING_BOOK_BORROWED_FOR_FORMAT = "Emprunté le %s par %s";
	private final String STRING_BOOK_NOT_BORROWED = "Aucune personne n'a emprunté ce livre";

	/* =======Initialization======= */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Init all needed data in window from a specified book.
	 * <p>
	 * This method needs to be called before showing the stage.
	 * 
	 * @param book  the book we want to display data
	 * @param stage the stage of the book page window
	 */
	public void initData(Book book, Stage stage) {
		// intit data stored in this page
		this.stage = stage;
		this.originalBook = book;
		this.isBorrowed = book.isBorrowed();
		this.borrower = book.getBorrower();
		this.borrowDate = book.getBorrowDate();
		this.category = book.getCategory();
		
		/* now fill all fields */
		titleLabel.setText(book.getTitle());
		authorLabel.setText(book.getAuthor());
		categoryLabel.setText(book.getCategory().getValue());
		descriptionTextArea.setText(book.getDescription());
		// for keywords, add them together and separate them with ;
		String keywords[] = book.getKeywords();
		StringJoiner joiner = new StringJoiner(";");
		for (int i = 0; i < keywords.length; i++) {
			joiner.add(keywords[i]);
		}
		keywordsTextArea.setText(joiner.toString());

		updateBorrowLabel();
	}

	/* =======Listeners methods======= */

	// Event Listener on Button[#modifyDescriptionButton].onAction
	@FXML
	public void modifyDescription(ActionEvent event) {
		TextAreaDialog textAreaDialog = new TextAreaDialog("Modifier la description",
				"Quelle-est la nouvelle description ?", descriptionTextArea.getText());
		Optional<String> result = textAreaDialog.showAndWait();
		if (result.isPresent()) {
			String newDescription = result.get();
			descriptionTextArea.setText(newDescription);
		}
	}

	// Event Listener on Button[#modifyKeywordsButton].onAction
	@FXML
	public void modifyKeywords(ActionEvent event) {
		KeywordsDialog keywordsDialog = new KeywordsDialog("Modifier les mots-clés", keywordsTextArea.getText());
		Optional<String> result = keywordsDialog.showAndWait();
		if(result.isPresent()) {
			keywordsTextArea.setText(result.get());
		}
	}

	// Event Listener on Button[#modifyTitleButton].onAction
	@FXML
	public void modifyTitle(ActionEvent event) {
		String newTitle = AlertHelper.showTextInputDialog("Modifier le titre",
				"Choisissez un nouveau titre pour le livre", "Titre", titleLabel.getText());
		titleLabel.setText(newTitle);
	}

	// Event Listener on Button[#modifyAuthorButton].onAction
	@FXML
	public void modifyAuthor(ActionEvent event) {
		String newAuthor = AlertHelper.showTextInputDialog("Modifier l'auteur",
				"Choisissez un nouvel auteur pour le livre", "Auteur", authorLabel.getText());
		authorLabel.setText(newAuthor);
		// don't forget to modify the book also
	}

	// Event Listener on Button[#modifyCategoryButton].onAction
	@FXML
	public void modifyCategory(ActionEvent event) {
		category = AlertHelper.showCategorySelectionDialog("Choix de categorie",
				"Quelle est la catégorie de ce livre ?", "Catégorie", Categories.fromValue(categoryLabel.getText()));
		categoryLabel.setText(category.getValue());
	}

	// Event Listener on Button[#saveModificationsButton].onAction
	@FXML
	public void saveModifications(ActionEvent event) {
		/* If we want to save, we will call #modifyBook() before close stage.
		 * If answer is no, we just have to close the stage.
		 * If answer is cancel, we don't do anything.
		 */
		int answer = AlertHelper.showYesNoCancelAlert("Sauvegarder les modifications ?",
				"Voulez-vous vraiment confirmer les modifications pour ce livre ?", null);
		if (answer != AlertHelper.CHOICE_CANCEL) {
			if (answer == AlertHelper.CHOICE_YES) {
				modifyBook();
				//don't forget to refresh the previous page
				AppContext.getInstance().getMainController().refreshListView();
			}
			stage.close();
		}
	}

	private void modifyBook() {
		originalBook.setTitle(titleLabel.getText());
		originalBook.setAuthor(authorLabel.getText());
		originalBook.setCategory(Categories.fromValue(categoryLabel.getText()));
		originalBook.setDescription(descriptionTextArea.getText());
		String keywords[] = keywordsTextArea.getText().split(";");
		originalBook.setKeywords(keywords);
		originalBook.setBorrowed(isBorrowed);
		originalBook.setBorrower(borrower);
		originalBook.setBorrowDate(borrowDate);
	}

	// Event Listener on Button[#deleteBookButton].onAction
	@FXML
	public void deleteBook(ActionEvent event) {
		if(AlertHelper.showYesNoAlert("Supprimer ?", "Voulez-vous vraiment supprimer le livre", "Ce choix n'aura de conséquences qu'après avoir sauvegardé.")) {
			LibraryManager.deleteBook(originalBook);
			//remove from the observable list on main page too
			AppContext.getInstance().getMainController().removeBookFromListView(originalBook);
			//and close !
			stage.close();
		}
	}

	// Event Listener on Button[#modifyBorrowButton].onAction
	@FXML
	public void modifyBorrow(ActionEvent event) {
		BorrowDialog borrowDialog = new BorrowDialog("Modifier l'emprunt", isBorrowed, borrower, borrowDate);
		Optional<HashMap<Integer, String>> result = borrowDialog.showAndWait();
		if(result.isPresent()) {
			HashMap<Integer, String> hm = result.get();
			isBorrowed = hm.get(BorrowDialog.FIELD_IS_BORROWED).equals("1");
			borrower = hm.get(BorrowDialog.FIELD_BORROWER);
			borrowDate = hm.get(BorrowDialog.FIELD_BORROW_DATE);
			updateBorrowLabel();
		}
	}

	/* =======Misc private methods======= */

	/**
	 * Call this method when want to update borrow label easily.<p>
	 * You must update accordingly the attributes <code>borrower, borrowDate and isBorrowed</code> before call this method.
	 */
	private void updateBorrowLabel() {
		// for borrow relative things, we first want to know if book is borrowed by
		// someone
		if (isBorrowed) {
			String formatedBorrowString = String.format(STRING_BOOK_BORROWED_FOR_FORMAT, borrowDate, borrower);
			borrowLabel.setText(formatedBorrowString);
		} else {
			borrowLabel.setText(STRING_BOOK_NOT_BORROWED);
		}
	}

}
