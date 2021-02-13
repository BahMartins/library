package br.com.caelum.library.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.library.dao.DAO;
import br.com.caelum.library.model.Author;
import br.com.caelum.library.model.Book;
import br.com.caelum.library.model.BookDataModel;

@ManagedBean
@ViewScoped
public class BookBean implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Book book = new Book();
	private Integer authorId;
	private Integer bookId;

	private List<Book> books;
	
	private List<String> genres = Arrays.asList("Romance", "Dama", "Action");
	
	private BookDataModel bookDataModel = new BookDataModel();
	
	public BookDataModel getBookDataModel() {
		return bookDataModel;
	}
	
	public List<String> getGenres() {
		return genres;
	}

	public void setBookDataModel(BookDataModel bookDataModel) {
		this.bookDataModel = bookDataModel;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getAuthorId() {
		return authorId;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}

	public Book getBook() {
		return book;
	}

	public List<Book> getBooks() {
		DAO<Book> dao =new DAO<Book>(Book.class);
		
		if(this.books == null) {
			this.books = dao.listAll();
		}
		
		return books;
	}

	public List<Author> getAuthors() {
		return new DAO<Author>(Author.class).listAll();
	}

	public List<Author> getBookAuthors() {
		return this.book.getAuthors();
	}

	public void addAuthor() {

		Author author = new DAO<Author>(Author.class).findById(this.authorId);
		this.book.addAuthor(author);
		
		System.out.println("Writing by: " + author.getName());
	}
	
	

	public void save() {
		System.out.println("Save Book " + this.book.getTitle());

		if (book.getAuthors().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("author", 
					new FacesMessage("The book cannot be save without Author"));
			return;
			
		}
		
		DAO<Book> dao = new DAO<Book>(Book.class);

		if(this.book.getId() == null) {
			dao.add(this.book);
			this.books = dao.listAll();
			
		} else {
			dao.update(this.book);
		}
		
		this.book = new Book();
	}
	
	public void remove(Book book) {
		System.out.println("Remove the book: " + book.getTitle() );
		new DAO<Book>(Book.class).remove(book);	
	}
	
	public void removeAuthorFromBook(Author author) {
		this.book.removeAuthor(author);
	}
	
	
	public void update(Book book) {
		System.out.println("Update the book: " + book.getTitle() );
		this.book = book;
	}
	
	public void findBookById() {
		this.book = new DAO<Book>(Book.class).findById(this.bookId);
	}
	
	public String formAuthor() {
		System.out.println("Call de author form");
		return "author?faces-redirect=true";
	}
	
	public void startWithDigitOne(FacesContext fc, UIComponent uic, Object obj) throws ValidatorException {
		String valor = obj.toString();
		if(!valor.startsWith("1"))
			throw new ValidatorException(new FacesMessage("Should  begin with number 1"));
	}
	
	public boolean priceIsLower(Object colummValue, Object typedFilter, Locale locale) {
		
		String typeText = (typedFilter == null) ? null : typedFilter.toString().trim();
		
		if(typeText == null || typeText.equals("")) return true;
		
		if(colummValue == null) return false;
		
		try {
			Double typePrice = Double.valueOf(typeText);
			Double colummPrice = (Double) colummValue;
			
			return colummPrice.compareTo(typePrice) < 0;
			
		} catch (NumberFormatException e) {
			return false;
		}
		
	}

	
	

}
