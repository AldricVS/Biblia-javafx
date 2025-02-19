package application.samples.bookListCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.misc.AlertHelper;
import application.samples.bookPage.BookPageController;
import data.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Implementation of Book display in a listview
 * @author Aldric Vitali Silvestre
 * @see Book
 */
public class BookListViewCellController extends ListCell<Book> implements Initializable{

	@FXML
	private Label titleLabel;
	@FXML
	private Label authorLabel;
	@FXML
	private Label categoryLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Button openButton;
	@FXML
	private AnchorPane bookListAnchorPane;
	
	private FXMLLoader loader;
	private Book book;
	
	@Override
	protected void updateItem(Book book, boolean empty) {
		super.updateItem(book, empty);
		this.book = book;
		
		if(empty || book == null) {
			
			setText(null);
			setGraphic(null);
			
		}else {
	
			//load fxml file if loader not defined
			if(loader == null) {
				loader = new FXMLLoader(getClass().getResource("BookListCell.fxml"));
				loader.setController(this);
				try {
					loader.load();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			titleLabel.setText(book.getTitle());
			authorLabel.setText(book.getAuthor());
			categoryLabel.setText(book.getCategory().getValue());
			descriptionLabel.setWrapText(true);
			descriptionLabel.setText(book.getDescription());
			
			setText(null);
			setGraphic(bookListAnchorPane);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		openButton.setOnAction(event -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookPage.fxml"));
				Parent parent = (Parent) fxmlLoader.load();
				BookPageController bookPageController = fxmlLoader.getController();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("Page du livre " + book.getTitle());
				stage.setScene(new Scene(parent));
				bookPageController.initData(book, stage);
				stage.show();
			}catch(IOException | RuntimeException e) {
				AlertHelper.showErrorAlert("Erreur !", "erreur lors de l'ouverture du livre", e.getMessage());
				throw new RuntimeException("Error while loading book window\n" + e);
			}
		});
	}
}
