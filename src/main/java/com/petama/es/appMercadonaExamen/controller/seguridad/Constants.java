package com.petama.es.appMercadonaExamen.controller.seguridad;

public class Constants {
  
//Spring Security
 public static final String LOGIN_URL 					= "/login";
 public static final String HEADER_AUTHORIZACION_KEY 	= "Authorization";
 public static final String TOKEN_BEARER_PREFIX 		= "Bearer ";
 public static final String AUTHORITIES					=  "authorities";
 public static final String USER_ID						=  "userId";

 // JWT
 public static final String ISSUER_INFO 				= "http://www.petama.com/";
 
 
 public static final String SUPER_SECRET_KEY 			= "r4u7x!A%D*G-KaP"; //123
 
 public static final long TOKEN_EXPIRATION_TIME 		= 86_400_000; // 1 day  86_400_000
 
 
}
