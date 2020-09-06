/**
 * 
 */
package process.helpers;

import data.Book;

/**
 * Helper class used in order to work more efficiently with csv strings
 * @author Aldric Vitali Silvestre
 */
public class CsvHelper {

	/**
	 * Split the csv line specified.<p>
	 * if elements between commas are between quotes, their commas will not be counted as separators.<p>
	 * It will also replace all </br> tags with newline character.
	 * @param csvLine the line to parse
	 * @return an array containing all elements sperated (without surronding quotes)
	 */
	public static String[] splitCsvLine(String csvLine) {
		String[] res = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		//remove quotes arround each element
		for(int i = 0; i < res.length; i++){
			res[i] = res[i].substring(1, res[i].length()-1);
		}
		return res;
	}

	/**
	 * Get all data in a book and create a new csv formated string.
	 * @param book the book which will be converted
	 * @return a csv string
	 */
	public static String bookToCsvLine(Book book) {
		//we have to get all attributes, modify them if needed
		String title = book.getTitle();
		String author = book.getAuthor();
		String category = book.getCategory().getValue();
		String keywords = book.getKeywordsOneLine();
		String description = book.getDescription().replaceAll(System.lineSeparator(), "</br>");
		return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
				title,
				author,
				category,
				keywords,
				description);
	}
	
}
