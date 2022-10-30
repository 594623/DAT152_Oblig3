package no.hvl.dat152.obl3.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Validator {

	public static String validString(String parameter) {
		return parameter != null ? parameter : "null";
	}
	
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	public static boolean validPassword(String password){
		boolean valid = true;
		if(password.length() < 8){
			valid = false;
		}
		String upperCase = "(.*[A-Z].*)";
		if(!password.matches(upperCase)){
			valid = false;
		}
		String numbers = "(.*[0-9].*)";
		if(!password.matches(numbers)){
			valid = false;
		}
		String space = "(.*[   ].*)";
		if(password.matches(space)){
			valid = false;
		}
		return valid;
	}

}
