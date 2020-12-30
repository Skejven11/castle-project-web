package com.castleproject.castle;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import com.castle.entities.Castle;

@Named
@RequestScoped
public class CastleViewBB {
	private static final String PAGE_CASTLE = "castleView?faces-redirect=true";
	
		Castle castle;
		
		public Castle getCastle()
		{
			return castle;
		}
	
	public String viewCastle(Castle castlee)
	{
		castle = castlee;
		return PAGE_CASTLE;
	}

}
