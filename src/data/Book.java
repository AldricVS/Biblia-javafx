package data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Genereal representation of a book
 * @author Aldric Vitali Silvestre
 */
public class Book implements Serializable{
	
	private String title;
	private String author;
	private Categories category;
	private String[] keywords;
	private String description;
	
	//attributes for borrow things
	private boolean isBorrowed;
	private String borrower;
	private String borrowDate;
	
	/**
	 * this is used for sorting results by relevance
	 */
	private int relevanceScore = 0;
	
	
	/**
	 * Create a new book with specified parameters
	 * @param title
	 * @param author
	 * @param category
	 * @param keywords
	 * @param description
	 * @param isBorrowed
	 * @param borrower
	 * @param borrowDate
	 */
	public Book(String title, String author, Categories category, String[] keywords, String description,
			boolean isBorrowed, String borrower, String borrowDate) {
		super();
		this.title = title;
		this.author = author;
		this.category = category;
		this.keywords = keywords;
		this.description = description;
		this.isBorrowed = isBorrowed;
		this.borrower = borrower;
		this.borrowDate = borrowDate;
	}

	/**
	 * Create a new book with specified parameters, and without borrow relatives attributes.
	 * @param title
	 * @param author
	 * @param category
	 * @param keywords
	 * @param description
	 */
	public Book(String title, String author, Categories category, String[] keywords, String description) {
		super();
		this.title = title;
		this.author = author;
		this.category = category;
		this.keywords = keywords;
		this.description = description;
		this.isBorrowed = false;
		this.borrower = "";
		this.borrowDate = "";
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the category
	 */
	public Categories getCategory() {
		return category;
	}

	/**
	 * @return the keywords
	 */
	public String[] getKeywords() {
		return keywords;
	}
	
	public String getKeywordsOneLine() {
		String res = "";
		int numberKeys = keywords.length - 1;
		for(int i = 0; i < numberKeys; i++) {
			res += keywords[i] + ";";
		}
		res += keywords[numberKeys];
		return res;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the score of the book
	 */
	public int getScore() {
		return relevanceScore;
	}
	
	/**
	 * @param value the new score of the book
	 */
	public void setScore(int value) {
		relevanceScore = value;
	}
	
	/**
	 * Add a specifed amount to the existing score of the book
	 * @param value the score to add
	 */
	public void addToScore(int value) {
		relevanceScore += value;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Categories category) {
		this.category = category;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the if book is Borrowed
	 */
	public boolean isBorrowed() {
		return isBorrowed;
	}

	/**
	 * @return the borrower
	 */
	public String getBorrower() {
		return borrower;
	}

	/**
	 * @return the borrowDate
	 */
	public String getBorrowDate() {
		return borrowDate;
	}

	/**
	 * @param isBorrowed the isBorrowed to set
	 */
	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	/**
	 * @param borrower the borrower to set
	 */
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	/**
	 * @param borrowDate the borrowDate to set
	 */
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", category=" + category + ", keywords="
				+ Arrays.toString(keywords) + ", description=" + description + "]";
	}
	
}
