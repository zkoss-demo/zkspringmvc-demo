package org.zkoss.zkspringmvc.demo.mvvm;

import java.util.List;

public class BooksVM {

	private List<Book> books;
	private Book currentBook;
	private boolean editable;

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean getEditable() {
		return editable;
	}
	public void setCurrentBook(Book book) {
		currentBook = book;
	}
	public Book getCurrentBook() {
		return currentBook;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> book) {
		books = book;
	}
}
