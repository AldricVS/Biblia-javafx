/**
 * 
 */
package data;

import java.io.IOException;
import java.util.ArrayList;

import process.helpers.FileHelper;

/**
 * Singleton class containing all books of the app
 * @author Aldric Vitali Silvestre
 */
public class Library {

	private static Library instance = new Library();
	
	private ArrayList<Book> bookList;
	
	private FileHelper fileHelper;
	
	/**
	 * Create a new Library, which will load the book file into the bookList of the library
	 */
	private Library() {
		//load the book file and the fileHelper for after
		try {
			fileHelper = new FileHelper();
			bookList = fileHelper.loadBookListCsv();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the only instance of Library.
	 */
	public static Library getInstance() {
		return instance;
	}
	
	/**
	 * Get all books in library 
	 * @return the list in the original ArrayList
	 */
	public ArrayList<Book> getBookList() {
		return bookList;
	}
	
	/**
	 * Add a new book in the library.<p>
	 * If this book already exists, nothing is done
	 * @param book the book to add
	 */
	public void addBook(Book book) {
		if(!bookList.contains(book)) {
			bookList.add(book);
		}
	}
}
