package process.managers;

import java.io.IOException;

import data.Book;

/**
 * Collection of methods relative to book management
 * @author Aldric Vitali Silvestre
 */
public class BookManager {

	private Book book;
	
	public BookManager(Book book) {
		this.book = book;
	}
	
	/**
	 * Add a new book in the book file
	 * @param book the book to add
	 * @throws IOException if error occurs while writing in books file
	 */
	public void createBook(Book book) throws IOException{
		
	}

}
