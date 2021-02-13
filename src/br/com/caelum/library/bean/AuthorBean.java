package br.com.caelum.library.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.library.dao.DAO;
import br.com.caelum.library.model.Author;
import br.com.caelum.library.model.Book;

@ManagedBean
@ViewScoped
public class AuthorBean {

	private Author author = new Author();
	private Integer authorId;
	


	public String submit() {
		System.out.println("Gravando autor " + this.author.getName());

		if(this.author.getId() == null) {
			new DAO<Author>(Author.class).add(this.author);
		} else {
			new DAO<Author>(Author.class).update(this.author);
		}

		this.author = new Author();
		return "book?faces-redirect=true";

	}



	public void authorIsPresent(FacesContext fc, UIComponent uic, Object obj) {
		String valor = obj.toString();

		List<Author> authors = new DAO<Author>(Author.class).listAll();

		for (int i = 0; i > authors.size(); i++) {

			if (authors.get(i).getName() == valor) {

				throw new ValidatorException(new FacesMessage("Author already exist"));
			}
		}
	}

	/*
	 * //Via Forward public ForwardView submit() { System.out.println("Save author "
	 * + this.author.getName()); new DAO<Author>(Author.class).add(this.author);
	 * this.author = new Author(); return new ForwardView("book"); }
	 */
	
	public void findAuthorById() {
		this.author = new DAO<Author>(Author.class).findById(authorId);
		
		if(this.author == null) {
			this.author = new Author();
		}
		
	}
	
	public List<Book> getBooks() {
		return new DAO<Book>(Book.class).listAll();
	}


	public void remove(Author author) {

		List<String> authors = getAuthors();
		
		for(int i = 0; i < authors.size(); i++) {
			
			if(author.getName().equals(authors.get(i))) {
				System.out.println("Not deleted");
				/*FacesContext.getCurrentInstance().addMessage("author",
	                    new FacesMessage("Author cannot be excluded for being associated with a Book."));*/
			}

		}
		new DAO<Author>(Author.class).remove(author);
		
		
    }
	
	
	public void update(Author author) {
		this.author = author;
	}
	
	
	public List<String> getAuthors() {
		List<Book> allBooks = getBooks();
		List<String> authors = new ArrayList<String>();
		
		for(Book book : allBooks) {
			List<Author> authors2 = book.getAuthors();
			for(Author author : authors2) {
				authors.add(author.getName());
			}
		}

		return authors;
				//authors.stream().map(item -> item.getName()).collect(Collectors.toList());
				//authors.stream().map(item -> item.getName()).equals(author.getName());
	}


	
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	
}
