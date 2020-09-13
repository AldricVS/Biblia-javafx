package process.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

import data.Book;
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
	private final String BACKUP_FOLDER_PATH = "data/Backups";
	
	private File booksFile = new File(BOOKS_LIST_PATH);
	private File backupFolder = new File(BACKUP_FOLDER_PATH);

	/**
	 * Create the fileHelper, and search for the books file.<p>
	 * Can also create a backup file if the previous launch was an anterior day
	 * @throws IOException 
	 */
	public FileHelper() throws IOException {
		if(!booksFile.exists()) {
			booksFile.createNewFile();
		}else {
			/*We want to have many backup files in case of a major bug
			 * We will store up to 5 backups files (we don't need backup of an empty file)*/
			if(!backupFolder.exists()) {
				backupFolder.mkdir();
			}else if(!backupFolder.isDirectory()) {
				backupFolder.delete();
				backupFolder.mkdir();
			}
			//try to check if another file with same name (i.e, created the same day) exists
			LocalDate localDate = LocalDate.now();
			String dateString = localDate.toString();
			String todayFileName = "Library_backup_" + dateString + ".csv";
			File todayFile = new File(backupFolder, todayFileName);
			if(!todayFile.exists()) {
				Files.copy(booksFile.toPath(), todayFile.toPath());
				//check if we have 5 files or less, else we have to delete the oldest 
				if(backupFolder.listFiles().length > 5) {
					removeOldestFile();	
				}
			}
		}
	}
	
	private void removeOldestFile() {
		File files[] = backupFolder.listFiles();
		//we have created one file before go in this method, so we can do this safely
		File oldestFile = files[0], tmpFile;
		for(int i = 1; i < files.length; i++) {
			tmpFile = files[i];
			long lastModifiedTmpFile = tmpFile.lastModified();
			long lastModifiedOldestFile = oldestFile.lastModified();
			//if file last modified attribute is smaller than the reference, it means that this file IS the oldest
			if(lastModifiedTmpFile < lastModifiedOldestFile) {
				oldestFile = tmpFile;
			}
		}
		//we finish by deletting the oldest file
		oldestFile.delete();
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
