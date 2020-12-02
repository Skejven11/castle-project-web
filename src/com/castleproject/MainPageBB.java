package com.castleproject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import com.castle.dao.CastleDAO;
import com.castle.entities.Castle;

@Named
@RequestScoped
public class MainPageBB {
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;
	
	ExternalContext extcontext;
	
	Flash flash;
	
	
	@EJB
	CastleDAO castleDAO;
		
	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public List<Castle> getFullList(){
		return castleDAO.getFullList();
	}

	public List<Castle> getList(){
		List<Castle> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
		}
		
		//2. Get list
		list = castleDAO.getList(searchParams);
		
		return list;
	}

	public String newCastle(){
		Castle castle = new Castle();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("castle", castle);
		
		return PAGE_PERSON_EDIT;
	}

	public String editCastle(Castle castle){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("castle", castle);
		
		return PAGE_PERSON_EDIT;
	}

	public String deletePerson(Castle castle){
		castleDAO.remove(castle);
		return PAGE_STAY_AT_THE_SAME;
	}
}
