package com.castleproject.castle;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.castle.entities.Castle;
import com.castle.dao.CastleDAO;
import com.castle.entities.CastleScore;
import com.castle.dao.CastelScoreDAO;
import com.castle.entities.User;
import com.castle.dao.UserDAO;

@Named
@RequestScoped
public class CastleViewBB {
	
		private Castle castle = new Castle();
		private ArrayList score = new ArrayList((int)castle.getScore());
		private CastleScore castleScore = new CastleScore();
		
		public CastleScore getCastleScore() {
			return castleScore;
		}
		
		public Castle getCastle() {
			return castle;
		}
		
		public ArrayList getScore() {
			return score;
		}
		
		@Inject
		FacesContext context;
		
		@EJB
		CastleDAO castleDAO;
		@EJB
		CastelScoreDAO castleScoreDAO;
		@EJB
		UserDAO userDAO;
		
		public void onLoad() throws IOException{
			castle = castleDAO.find(castle.getIdcastle());
			for (int i=0;i<castle.getScore();i++) score.add("");
		}
		
		public String castleScored() {
			List<User> users = userDAO.getUser("admin", "admin");
			User user = users.get(0);
			Date date = new Date();
			castleScore.setCastle(castle);
			castleScore.setUser(user);
			castleScore.setDate(date);
			
			try {
				castleScoreDAO.create(castleScore);

			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				return null;
			}
			return null;
		}

}
