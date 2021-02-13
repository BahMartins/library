package br.com.caelum.library.model;

public class Sale {
	
	private Book book;
	private Integer quantity;
	
	
	public Sale(Book book, Integer quantity) {
		this.book = book;
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
