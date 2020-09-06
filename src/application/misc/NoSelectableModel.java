package application.misc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * Selection model class which avoids to select any of the elements of a list.
 * <p>
 * Used in the book list in the main page.
 * 
 * @author Aldric Vitali Silvestre
 * @param <T>
 */
public class NoSelectableModel<T> extends MultipleSelectionModel<T> {

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public ObservableList<T> getSelectedItems() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public void selectAll() {

	}

	@Override
	public void selectFirst() {

	}

	@Override
	public void selectIndices(int arg0, int... arg1) {

	}

	@Override
	public void selectLast() {

	}

	@Override
	public void clearAndSelect(int arg0) {

	}

	@Override
	public void clearSelection() {

	}

	@Override
	public void clearSelection(int index) {

	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isSelected(int index) {
		return false;
	}

	@Override
	public void select(int index) {
	}

	@Override
	public void select(T obj) {

	}

	@Override
	public void selectNext() {

	}

	@Override
	public void selectPrevious() {

	}

}
