package process.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale.Category;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.*;
import process.helpers.FileHelper;

/**
 * 
 * @author Aldric Vitali Silvestre
 *
 */
public class LibraryManager {

	private static final String uselessWords[] = { "le", "la", "les", "une", "un", "des", "de", "du" };

	private static final int SCORE_TITLE = 5;
	private static final int SCORE_AUTHOR = 7;
	private static final int SCORE_CATEGORY = 30;
	private static final int SCORE_KEYWORDS = 10;
	private static final int SCORE_DESCRIPTION = 1;

	private LibraryManager() {
	}

	/**
	 * Search books by relevance. If too many are corresponding, only the 15 most
	 * relevant will be returned
	 * 
	 * @param searchInput the string containing data to search
	 * @return the 15 most relevant books at max
	 */
	public static BookList searchBooks(String searchInput) {
		Library library = Library.getInstance();
		ArrayList<String> searchWords = splitSearchInput(searchInput);

		/*
		 * we will try to find occurences od input words at all place in each book
		 * Depending on where the occurence is found, the score added will be different
		 */
		int score;
		ArrayList<Book> books = library.getBookList();
		ArrayList<Book> booksFound = new ArrayList<Book>();
		for (Book book : books) {
			// don't forget to reset the score
			score = 0;

			// the score depending on the title, author and description are simple to
			// calculate
			score += scoreInString(book.getTitle(), searchWords, SCORE_TITLE);
			score += scoreInString(book.getAuthor(), searchWords, SCORE_AUTHOR);
			score += scoreInString(book.getDescription(), searchWords, SCORE_DESCRIPTION);

			for (String word : searchWords) {
				// for keywords, we have to iterate in the array of keywords
				for (String keyword : book.getKeywords()) {
					if (keyword.toLowerCase().equals(word)) {
						score += SCORE_KEYWORDS;
					}
				}
				// category is simpler because one word
				if (book.getCategory().getValue().toLowerCase().equals(word)) {
					score += SCORE_CATEGORY;
				}
			}

			// finally, we can add the book with the score in the hashmap
			if (score != 0) {
				book.addToScore(score);
				booksFound.add(book);
			}

		}

		// sorting time !
		Collections.sort(booksFound, new ScoreComparator().reversed());

		// we only want the first 15 elements (if available)
		BookList bookList = new BookList();
		for (int i = 0; i < 15; i++) {
			if (i < booksFound.size()) {
				bookList.addBook(booksFound.get(i));
			} else {
				break;
			}
		}
		return bookList;
	}

	public static void saveLibrary() throws IOException {
		FileHelper fh = new FileHelper();
		fh.saveBookListCsv(Library.getInstance().getBookList());
	}

	/**
	 * Calculate a score depending on the number of occurences of a list of words in
	 * a desired string.
	 * 
	 * @param stringToSearch the string where search occurences
	 * @param searchWords    a list of words to search
	 * @param scoreToAdd     the score to add at each occurence found
	 * @return the total score
	 */
	private static int scoreInString(String stringToSearch, ArrayList<String> searchWords, int scoreToAdd) {
		int score = 0;
		String lowerCaseString = stringToSearch.toLowerCase();
		for (String word : searchWords) {
			if (containsWholeWord(lowerCaseString, word)) {
				score += scoreToAdd;
			}
		}
		return score;
	}

	private static ArrayList<String> splitSearchInput(String searchInput) {
		// prepare stop words list
		ArrayList<String> stopWords = new ArrayList<String>();
		Collections.addAll(stopWords, uselessWords);

		// add all words in a new list (the search is not case-sensitive)
		String wordsArray[] = searchInput.toLowerCase().split(" ");

		// we want to remove all useless words from search input first
		ArrayList<String> wordsList = new ArrayList<String>();
		Collections.addAll(wordsList, wordsArray);
		wordsList.removeAll(stopWords);

		return wordsList;
	}

	private static boolean containsWholeWord(String source, String word) {
		String patternString = "\\b" + word + "\\b";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(source);
		return matcher.find();
	}

	/**
	 * Class used to comparate score between to books
	 * 
	 * @author Aldric Vitali Silvestre
	 */
	static class ScoreComparator implements Comparator<Book> {
		@Override
		public int compare(Book o1, Book o2) {
			Integer score1 = o1.getScore();
			Integer score2 = o2.getScore();
			return score1.compareTo(score2);
		}

	}

	/**
	 * Delete a book from the library book list 
	 * @param book the book to remove
	 * @return if book was in the list
	 */
	public static boolean deleteBook(Book book) {
		return Library.getInstance().getBookList().remove(book);
	}
}
