package no.hvl.dat152.obl3.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Validator {

	public static String validString(String parameter) {
		return parameter != null ? parameter : "null";
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {

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

	public static String validInput(String parameter) {
		String regex = "^[a-zæøåA-ZÆØÅ]+$";

		if (parameter.matches(regex) && parameter != null) {
			return parameter;
		} else {
			return "Error, invalid string";
		}
	}

	public static boolean validPassword(String password) {
		boolean valid = true;
		if (password.length() < 8) {
			valid = false;
		}
		String upperCase = "(.*[A-Z].*)";
		if (!password.matches(upperCase)) {
			valid = false;
		}
		String numbers = "(.*[0-9].*)";
		if (!password.matches(numbers)) {
			valid = false;
		}
		String space = "(.*[   ].*)";
		if (password.matches(space)) {
			valid = false;
		}
		return valid;
	}
	
	public static String validUsername(String username) {
		String regex = "^[a-zæøåA-ZÆØÅ0-9]+$";
		
		if(username.matches(regex) && username != null) {
			return username;
		} else {
			return "Error, invalid username";
		}
	}

	//CSRF
	public static boolean csrfValidity(HttpServletRequest r) {
        String a = "";
        try {
            a = (String) r.getSession().getAttribute("anticsrf");
        } catch (NullPointerException e) {
            return true;
        	}
        String b = r.getParameter("anticsrf");
        return !a.equals(b);
    }
	
	public static String validPhone(String username) {
		String regex = "^[0-9]+$";
		
		if(username.matches(regex) && username != null) {
			return username;
		} else {
			return "Error, invalid username";
		}
	}
	
}
