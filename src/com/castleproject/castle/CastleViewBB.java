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
		private static final String PAGE_CASTLE = "castleView?faces-redirect=true&includeViewParams=true";
		private Castle castle = new Castle();
		private ArrayList scores = new ArrayList((int)castle.getScore());
		private CastleScore castleScore = new CastleScore();
		private User user = new User();
		private int score = 0;
		
		public CastleScore getCastleScore() {
			return castleScore;
		}
		
		public Castle getCastle() {
			return castle;
		}
		
		public User getUser() {
			return user;
		}
		
		public ArrayList getScores() {
			return scores;
		}
		
		public Integer getScore() {
			return score;
		}
		
		public void setScore(Integer score) {
			this.score=score;
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
			for (int i=0;i<castle.getScore();i++) scores.add("");
			
			List<User> users = userDAO.getUser("admin", "admin");
			user = users.get(0);
			
			List<CastleScore> castleScores=castleScoreDAO.scoreExists(user, castle);
			if (!castleScores.isEmpty()) castleScore = castleScores.get(0);
		}
		
		public String castleScored() {
			castle = castleDAO.find(castle.getIdcastle());
			Date date = new Date();
			List<User> users = userDAO.getUser("admin", "admin");
			user = users.get(0);
			
			List<CastleScore> castleScores=castleScoreDAO.scoreExists(user, castle);
			if (!castleScores.isEmpty()) castleScore=castleScores.get(0);
			castleScore.setCastle(castle);
			castleScore.setUser(user);
			castleScore.setDate(date);
			castleScore.setRating(score);
			
			try {
				if (castleScores.isEmpty()) {
					castleScoreDAO.create(castleScore);
				}
				else castleScoreDAO.merge(castleScore);

			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				return null;
			}
			calcScore();
			return PAGE_CASTLE;
		}
		
		public void calcScore() {
			List<CastleScore> scores = castleScoreDAO.scoreCastles(castle);
			double rating=0;
			int size=0;
			
			for (CastleScore scoreTemp: scores) {
				rating+=scoreTemp.getRating();
				size++;
			}
			castle.setScore(rating/size);
			
			try {
					castleDAO.merge(castle);

			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
			}
		}

}
