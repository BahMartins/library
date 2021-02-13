package br.com.caelum.library.util;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.caelum.library.model.Book;

public class LazySorter implements Comparator<Book>{
	
	private String sortField;
	private SortOrder sortOrder;

	
	 public LazySorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	
	@Override
	public int compare(Book book1, Book book2) {

		try {
			Field field = Book.class.getDeclaredField(this.sortField);
			field.setAccessible(true);
			
			Object value1 = field.get(book1);
			Object value2 = field.get(book2);
			field.setAccessible(false);
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			int value = ((Comparable) value1).compareTo(value2);
			
			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
			
	}

}
