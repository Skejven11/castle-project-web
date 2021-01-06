package com.castleproject.castle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.castle.entities.Castle;
import com.castle.dao.CastleDAO;

@Named
@RequestScoped
public class CastleViewBB {
	private static final String PAGE_CASTLE = "castleView?faces-redirect=true";
	
		private Castle castle = new Castle();
		private String ruin;
		private int score;
		
		public String getRuin() {
			return ruin;
		}
		
		public Castle getCastle() {
			return castle;
		}
		
		public int getScore() {
			return score;
		}
		
		@Inject
		FacesContext context;
		
		@EJB
		CastleDAO castleDAO;
		
		public void onLoad() throws IOException{
			castle = castleDAO.find(castle.getIdcastle());
			if (castle.getIsRuin()==0) ruin = "Well maintained";
			else ruin = "Ruins";
			score = 6;
			
		}

}
