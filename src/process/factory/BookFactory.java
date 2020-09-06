package process.factory;

import data.Book;
import data.Categories;

/**
 * Factory class used to create books
 * @author Aldric Vitali Silvestre
 *
 */
public class BookFactory {

	/**
	 * Create a new book from csv string previously splitted
	 * @param csvSplit the array containg string data
	 * @return a whole new book
	 * @throws IllegalArgumentException if csvSplit is not a valid array
	 */
	public static Book createBook(String csvSplit[]) throws IllegalArgumentException{
		try {
			String titleString = csvSplit[0];
			String authorString = csvSplit[1];
			String categoryString = csvSplit[2];
			String keywordsString = csvSplit[3];
			String descriptionString = csvSplit[4];
			
			//we have to change some fileds to be usable
			Categories category = Categories.fromValue(categoryString);
			String keywords[] = keywordsString.split(";");
			descriptionString = descriptionString.replaceAll("</br>", System.lineSeparator());
			
			//now, we can create the book
			return new Book(titleString, authorString, category, keywords, descriptionString);
			
		}catch(IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Array provided not valid : " + e);
		}
	}
	
}
