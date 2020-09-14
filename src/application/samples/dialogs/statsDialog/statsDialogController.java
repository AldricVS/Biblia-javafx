package application.samples.dialogs.statsDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Book;
import data.Categories;
import data.Library;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class statsDialogController implements Initializable{
	@FXML
	private Label numberBooksLabel;
	@FXML
	private PieChart booksByCategoryChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//we will get informations from library directly
		int numberBooks = Library.getInstance().getBookList().size();
		numberBooksLabel.setText(String.valueOf(numberBooks));
		
		/*now, the chart part*/
		//init all needed variables
		Categories categories[] = Categories.values();
		int numberCategories = categories.length;
		ArrayList<Book> books = Library.getInstance().getBookList();
		int numberBooksByCategory[] = new int[numberCategories]; //init all values to 0 by default
		
		//we will get the ordinal of the category of the book and add it to the numberBooksByCategoryArray
		for(Book book : books) {
			Categories category = book.getCategory();
			int ordinal = category.ordinal();
			numberBooksByCategory[ordinal]++;
		}
		
		//we can finally create each slice and add it to the chart
		for(int i = 0; i < numberCategories; i++) {
			String categoryName = categories[i].getValue();
			int numberBooksForThisCategory = numberBooksByCategory[i];
			String categoryWithNumberName = String.format("%s : %d", categoryName, numberBooksForThisCategory);
			PieChart.Data slice = new PieChart.Data(categoryWithNumberName, numberBooksForThisCategory);
			booksByCategoryChart.getData().add(slice);
		}
	}

}
