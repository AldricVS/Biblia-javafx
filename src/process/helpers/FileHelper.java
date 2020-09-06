package process.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import data.Book;
import data.BookList;
import process.factory.BookFactory;

/**
 * Helper class used to handle app specific files. Books file is stored in csv
 * format.
 * <p>
 * In the file, book informations are stored like that :
 * 
 * <pre>
 * "title","author","category","keywords","description"
 * </pre>
 * 
 * <code>""</code> are arround each line in order to avoid commas in fields to
 * be confused with separating commas
 * 
 * @author Aldric Vitali Silvestre
 */
public class FileHelper {
	private final String BOOKS_LIST_PATH = "data/bookslist.csv";

	private final String BOOK_OBJ_PATH = "data/books.ser";
	
	private File booksFile = new File(BOOKS_LIST_PATH);
	private File booksObjectsFile = new File(BOOK_OBJ_PATH);

	/**
	 * Create the fileHelper, and search for the books file.
	 * @throws IOException 
	 */
	public FileHelper() throws IOException {
		if(!booksFile.exists()) {
			booksFile.createNewFile();
		}
	}
	
	/**
	 * Save the book list as in file serialized
	 * @param bookList the list of books to save (generrally, the one which contains ALL books)
	 * @throws IOException if an error occurs while saving
	 */
	public void saveBookList(BookList bookList) throws IOException{
		FileOutputStream fs = new FileOutputStream(booksObjectsFile);
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(bookList);
		os.close();
	}
	
	/**
	 * Get the main book list from the .ser file
	 * @return the list containing all books
	 * @throws IOException if an error occured while reading bookslist
	 */
	public BookList loadBookList() throws IOException, ClassNotFoundException{
		FileInputStream fs = new FileInputStream(booksObjectsFile);
		ObjectInputStream is = new ObjectInputStream(fs);
		
		BookList bookList = null;
		try {
			bookList = (BookList)is.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		is.close();
		return bookList;
	}
	
	/**
	 * Get all books that are stored in the books list file 
	 * @return an array list containg books
	 * @throws IOException if an error occured while reading file
	 */
	public ArrayList<Book> loadBookListCsv() throws IOException{
		
		FileReader fileReader = new FileReader(booksFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		Book book;
		while((line = bufferedReader.readLine()) != null) {
			String splittedLine[] = CsvHelper.splitCsvLine(line);
			try {
				book = BookFactory.createBook(splittedLine); 
				bookList.add(book);
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		bufferedReader.close();
		return bookList;
	}
	
	/**
	 * Save the specified book list in the csv file 
	 * @param bookList the book list to save
	 * @throws IOException if an error occurs while saving
	 */
	public void saveBookListCsv(ArrayList<Book> bookList) throws IOException{
		FileWriter fileWriter = new FileWriter(booksFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		int listSize = bookList.size();
		Book book;
		for(int i = 0; i < listSize; i++) {
			book = bookList.get(i);
			String csvLine = CsvHelper.bookToCsvLine(book);
			bufferedWriter.write(csvLine);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

}
