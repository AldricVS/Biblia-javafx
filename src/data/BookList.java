package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * List of books obtained generally after a search by user
 * @author Aldric Vitali Silvestre
 */
public class BookList implements Serializable{
	
	private ArrayList<Book> books;

	public BookList() {
		books = new ArrayList<Book>();
	}
	
	public BookList(ArrayList<Book> bookList) {
		books = bookList;
	}

	/**
	 * @return the books
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}
	
	public int getBookListSize() {
		return books.size();
	}
	
	/**
	 * Get a single book in the book list 
	 * @param bookIndex where book is positionned in list
	 * @return a book
	 * @throws IndexOutOfBoundsException if size of list is smaller or equal than bookIndex
	 */
	public Book getBook(int bookIndex) throws IndexOutOfBoundsException{
		return books.get(bookIndex);
	}
	
	/**
	 * Add a specified book in the books list
	 * @param book the book to add
	 */
	public void addBook(Book book) {
		books.add(book);
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("BookList [");
		builder.append(System.lineSeparator());
		for(Book book : books) {
			builder.append(book.toString());
			builder.append(System.lineSeparator());
		}
		builder.append("]");
		return builder.toString();
	}
	
	

}
