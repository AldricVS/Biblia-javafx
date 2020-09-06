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
	
	/**
	 * this is used for sorting results by relevance
	 */
	private int relevanceScore = 0;
	
	/**
	 * Create a new book with specified parameters.
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
		for(int i = 0; i <= numberKeys; i++) {
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

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", category=" + category + ", keywords="
				+ Arrays.toString(keywords) + ", description=" + description + "]";
	}
	
}
