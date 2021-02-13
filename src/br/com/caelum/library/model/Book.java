package br.com.caelum.library.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;
	private String isbn;
	private double price;
	private String genre;
	
	@Temporal(TemporalType.DATE)
	private Calendar releaseDate = Calendar.getInstance();

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Author> authors = new ArrayList<Author>();

	public List<Author> getAuthors() {
		return authors;
	}
	
	public void addAuthor(Author author) {
		if (!this.authors.contains(author)) {
			this.authors.add(author);
		}
	}

	public Book() {
	}

	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void removeAuthor(Author author) {
		this.authors.remove(author);
	}



}
