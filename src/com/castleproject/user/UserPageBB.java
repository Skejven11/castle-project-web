package com.castleproject.user;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;

import com.castle.entities.Castle;
import com.castle.dao.CastleDAO;
import com.castle.entities.CastleScore;
import com.castle.dao.CastelScoreDAO;
import com.castle.entities.User;
import com.castle.dao.UserDAO;

@Named
@RequestScoped
public class UserPageBB {
		private Castle castle = new Castle();
		private User user = new User();
		private List<CastleScore> scoreList= null;
		
		public List<CastleScore> getScoreList() {
			return scoreList;
		}
		
		public Castle getCastle() {
			return castle;
		}
		
		public User getUser() {
			return user;
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
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			RemoteClient<User> userTemp = (RemoteClient<User>)session.getAttribute("remoteClient");
			user = userDAO.find(userTemp.getDetails().getIduser());
			scoreList = castleScoreDAO.userExists(user);
		}

}
