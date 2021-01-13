package com.castleproject.register;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.castle.dao.UserDAO;
import com.castle.entities.User;

@Named
@ViewScoped
public class RegisterBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_LOGIN = "/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private User user = new User();
	
	public User getUser() {
		return user;
	}

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public String saveData() {
		
		user.setRole("user");
		
		if (userDAO.userExists(user.getLogin(), user.getPassword()).isEmpty())
		{
			try {
					userDAO.create(user);
	
			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				return PAGE_STAY_AT_THE_SAME;
			}
		}
		else
		{
			context.addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already exists", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_LOGIN;
	}
}
