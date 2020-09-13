/**
 * 
 */
package test;

import data.BookList;
import process.managers.LibraryManager;

/**
 * 
 * @author Aldric Vitali Silvestre
 *
 */
public class BooksTest {

	/**
	 * Entry point of this test
	 * @param args
	 */
	public static void main(String[] args) {
		BookList bookList = LibraryManager.searchBooks("banane");
		System.out.println(bookList);
	}
	
	

}
