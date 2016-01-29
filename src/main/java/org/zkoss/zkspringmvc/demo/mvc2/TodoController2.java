/* TodoController.java

	Purpose:
		
	Description:
		
	History:
		2:18 PM 11/16/15, Created by jumperchen

Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.zkspringmvc.demo.mvc2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.zkoss.zkspringmvc.ZKModelAndView;
import org.zkoss.zkspringmvc.annotation.ZKSelector;
import org.zkoss.zkspringmvc.annotation.ZKVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 * @author jumperchen
 */
@Controller
@RequestMapping("/mvc/todos2")
@SessionAttributes("todoList")
public class TodoController2 {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap model) {
		ListModelList<Todo> todoList = new ListModelList<Todo>();
		todoList.add(new Todo("Wakeup early"));
		todoList.add(new Todo("Booking a restaurant"));
		todoList.add(new Todo("Visit Jobs' office"));
		model.addAttribute(todoList);

		return "forward:list";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ZKVariable("self.value") Todo todo) {
		todo.setDone(!todo.isDone());
		return ZKModelAndView.SELF;
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list() {
		return "mvc2/list.zul";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@ModelAttribute("todoList") ListModelList<Todo> todoList,
			@ZKSelector("#message") String message) {
		todoList.add(new Todo(message));
		return "forward:list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(
			@ModelAttribute("todoList") ListModelList<Todo> todoList,
			@ZKSelector("#status") Label status,
			@ZKSelector("#message") Textbox message,
			@ZKSelector("#submit") Button submit) {
		Todo editTodo = todoList.getSelection().iterator().next();
		message.setValue(editTodo.getMessage());

		status.setValue("Edit:");

		submit.setLabel("Update");
		submit.setClientDataAttribute("springmvc-action", "update");

		return ZKModelAndView.SELF;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("todoList") ListModelList<Todo> todoList,
			@ZKSelector("#status") Label status,
			@ZKSelector("#message") String message,
			@ZKSelector("#submit") Button submit) {

		Todo editTodo = todoList.getSelection().iterator().next();
		editTodo.setMessage(message);

		status.setValue("Add:");

		submit.setLabel("Add new Todo");
		submit.setClientDataAttribute("springmvc-action", "add");

		//save to model
		todoList.notifyChange(editTodo);

		return "forward:list";
	}

	@RequestMapping(value = "/changeTemplate",  method = RequestMethod.POST)
	public String changeTemplate(@ZKVariable("self.checked") Boolean checked) {
		return checked ? "::shadow:memo" : "::shadow:todo";
		//in this version of zkspringmvc, "::shadow" would only select the first shadow root instead of selecting all shadow roots.
	}

}
