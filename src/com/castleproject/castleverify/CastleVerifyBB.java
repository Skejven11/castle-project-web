package com.castleproject.castleverify;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class CastleVerifyBB {
		private List<Castle> list = null;
		
		public List<Castle> getList() {
			List<Castle> list = null;
			list = castleDAO.getListNonVer();
			return list;
		}
		
		@Inject
		FacesContext context;
		
		@EJB
		CastleDAO castleDAO;
		
		public String verify (int id) {
			Castle castle = new Castle();
			castle = castleDAO.find(id);
			castle.setIsVerified("1");
			
			try {
				castleDAO.merge(castle);

			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				return null;
			}
			return null;
		}
		
		public Void reject(int id) {
			Castle castle = new Castle();
			castle = castleDAO.find(id);
			
			try {
				castleDAO.remove(castle);

			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				return null;
			}
			return null;
		}

}
