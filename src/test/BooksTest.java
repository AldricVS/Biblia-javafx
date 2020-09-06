/**
 * 
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import data.Book;
import data.BookList;
import data.Categories;
import process.helpers.FileHelper;
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
