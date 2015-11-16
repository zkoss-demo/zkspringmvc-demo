/* BooksController.java

	Purpose:
		
	Description:
		
	History:
		5:53 PM 11/9/15, Created by jumperchen

Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkspringmvc.demo.mvvm;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.zkoss.zkspringmvc.ZKModelAndView;
import org.zkoss.zkspringmvc.annotation.ZKCommandLifecycle;
import org.zkoss.zkspringmvc.annotation.ZKNotifyChange;
import org.zkoss.zkspringmvc.annotation.ZKSelector;
import org.zkoss.zkspringmvc.annotation.ZKVariable;

/**
 * @author jumperchen
 */
@Controller
@RequestMapping("/mvvm/books")
@SessionAttributes({"booksVM"})
public class BooksController {
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String index(ModelMap model) {
		String[][] BOOKS = {{"The Very Hungry Caterpillar", "Eric Carle",
				"Children,Classics,Animals"},
				{"The New Way Things Work", "David Macaulay",
						"Education,Science,Computers"},
				{"The DASH Diet Younger You", "Marla Heller",
						"Health,Fitness,Diets"}};
		BooksVM books = new BooksVM();
		books.setBooks(init(BOOKS));
		books.setCurrentBook(books.getBooks().get(0));

		model.addAttribute(books);
		return "mvvm/index.zul";
	}

	private LinkedList<Book> init(String[][] BOOKS) {
		LinkedList<Book> books = new LinkedList<Book>();
		for (String[] book : BOOKS) {
			books.add(initBook(book[0], book[1], book[2]));
		}
		return books;
	}

	private Book initBook(String name, String author, String categories) {
		Book book = new Book();
		book.setName(name);
		book.setAuthor(author);
		String[] cates = categories.split(",");
		Set<Category> sets = new LinkedHashSet<Category>();
		for (String cate : cates) {
			Category c = new Category();
			c.setName(cate);
			sets.add(c);
		}
		book.setCategories(sets);
		return book;
	}

	@RequestMapping(value = "/addCate", method = RequestMethod.POST)
	@ZKCommandLifecycle
	public String doAddCategory(@ZKVariable Book fx,
			@ZKSelector String cateName) {
		Set<Category> categories = (Set<Category>) fx.getCategories();
		categories.add(new Category(cateName));
		return ZKModelAndView.SELF;
	}

	@RequestMapping(value = "/removeCate", method = RequestMethod.POST)
	public String doRemoveCategory(@ZKVariable Book fx,
			@ZKVariable Category each) {
		Set<Category> categories = (Set<Category>) fx.getCategories();
		categories.remove(each);
		return ZKModelAndView.SELF;
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ZKCommandLifecycle
	public @ZKNotifyChange({"vm.currentBook"})
	ZKModelAndView onReset(@ModelAttribute BooksVM booksVM) {
		ZKModelAndView model = new ZKModelAndView();
		model.addObject("vm", booksVM);
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ZKCommandLifecycle
	public @ZKNotifyChange({"editable", "currentBook"})
	BooksVM onSave(@ModelAttribute BooksVM booksVM) {
		booksVM.setEditable(false);
		return booksVM;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ZKNotifyChange("editable")
	BooksVM edit(@ModelAttribute BooksVM booksVM) {
		booksVM.setEditable(true);
		return booksVM;
	}
}
