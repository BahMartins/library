package br.com.caelum.library.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.library.dao.DAO;
import br.com.caelum.library.util.LazySorter;

public class BookDataModel extends LazyDataModel<Book>{

	private static final long serialVersionUID = 1L;
	
	DAO<Book> dao = new DAO<Book>(Book.class);
	
	public BookDataModel() {
		super.setRowCount(dao.quantityOfElements());
	}
	
	@Override
	public List<Book> load(int begin, int amount, String sortingField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
		
		if(filters != null) {
			for(FilterMeta meta : filters.values()) {
				
				String filterField = meta.getFilterField();
				Object filterValue = meta.getFilterValue();
				
				if(filterField.equalsIgnoreCase("title") && filterValue != null) {
					List<Book> data = dao.listAllPaged(begin, amount, "title", filterValue.toString());
					return data;
				}
			}
			
		}
		
		List<Book> data = dao.listAllPaged(begin, amount, null, null);
		
		if(sortingField != null) {
			Collections.sort(data, new LazySorter(sortingField, sortOrder));
			
		}
		
		return data;
	}

}
