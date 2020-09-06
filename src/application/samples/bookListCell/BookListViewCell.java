package application.samples.bookListCell;

import java.io.IOException;

import data.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Implementation of Book display in a listview
 * @author Aldric Vitali Silvestre
 * @see Book
 */
public class BookListViewCell extends ListCell<Book>{

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
	
	@Override
	protected void updateItem(Book book, boolean empty) {
		super.updateItem(book, empty);
		
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
	
	/*public BookCell(Book book){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BookListCell.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		loadInfos(book);
	}

	private void loadInfos(Book book) {
		titleLabel.setText(book.getTitle());
		authorLabel.setText(book.getAuthor());
		categoryLabel.setText(book.getCategory().getValue());
		descriptionArea.setText(book.getDescription());
	}*/
	
}
