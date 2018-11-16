package com.bridgelabz.login.utility;

import io.jsonwebtoken.Jwts;

public class VerifyJwtToken {
	private static final String KEY="piyush19";
	
	public static String  getId(String token)
	 {
		 return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getId();
		
	 }
}