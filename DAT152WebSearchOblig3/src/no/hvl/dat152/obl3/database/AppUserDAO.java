package no.hvl.dat152.obl3.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import no.hvl.dat152.obl3.util.Crypto;


public class AppUserDAO {

  public AppUser getAuthenticatedUser(String username, String password) {

    String hashedPassword = Crypto.generateMD5Hash(password);
    
    String sql = "SELECT * FROM SecOblig.AppUser"
    		+ " WHERE username = ? AND passhash = ?";
    
    AppUser user = null;

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      s.setString(1, username);
      s.setString(2, hashedPassword);
      r = s.executeQuery();

      if (r.next()) {
        user = new AppUser(
            r.getString("username"),
            r.getString("passhash"),
            r.getString("firstname"),
            r.getString("lastname"),
            r.getString("mobilephone"),
            r.getString("role"),
            r.getString("clientId")
            );
      }

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }

    return user;
  }
  
  public String getUserClientID(String mobilephone) {

    String sql = "SELECT clientId FROM SecOblig.AppUser" 
    		+ " WHERE mobilephone = ?";
    
    
    String clientID = null;

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      s.setString(1, mobilephone);
      r = s.executeQuery();

      if (r.next()) {
        clientID = r.getString("clientId");
      }

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
    
    return clientID;
  }
  
  public boolean clientIDExist(String clientid) {

	    String sql = "SELECT clientId FROM SecOblig.AppUser" 
	    		+ " WHERE clientId = ?";
	    
	    
	    String clientID = null;

	    Connection c = null;
	    PreparedStatement s = null;
	    ResultSet r = null;

	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.prepareStatement(sql);
	      s.setString(1, clientid);  
	      r = s.executeQuery();

	      if (r.next()) {
	        clientID = r.getString("clientId");
	      }

	    } catch (Exception e) {
	      System.out.println(e);
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    
	    return clientID != null;
	  }

  public boolean saveUser(AppUser user) {
    
    String sql = "INSERT INTO SecOblig.AppUser VALUES ("
    		+ "?, ?, ?, ?, ?, ?, ?)";

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      s.setString(1, user.getUsername());
      s.setString(2, user.getPasshash());
      s.setString(3, user.getFirstname());
      s.setString(4, user.getLastname());
      s.setString(5, user.getMobilephone());
      s.setString(6, user.getRole());
      s.setString(7, user.getClientID());
      int row = s.executeUpdate();
      if(row >= 0)
    	  return true;
    } catch (Exception e) {
    	System.out.println(e);
    	return false;
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
    
    return false;
  }
  
  	public List<String> getUsernames() {
	  
	  	List<String> usernames = new ArrayList<String>();
	  
	  	String sql = "SELECT username FROM SecOblig.AppUser";

		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;
		
		try {        
		  c = DatabaseHelper.getConnection();
		  s = c.prepareStatement(sql);     
		  r = s.executeQuery();
		
		  while (r.next()) {
			  usernames.add(r.getString("username"));
		  }
		
		} catch (Exception e) {
		  System.out.println(e);
		} finally {
		  DatabaseHelper.closeConnection(r, s, c);
		}
	  
		return usernames;
  	}
  
  public boolean updateUserPassword(String username, String passwordnew) {
	  
	  String hashedPassword = Crypto.generateMD5Hash(passwordnew);
	  
	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET passhash = ? "
	    				+ "WHERE username = ?";
	
	    Connection c = null;
	    PreparedStatement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.prepareStatement(sql);   
	      s.setString(1, hashedPassword);
	      s.setString(2, username);
	      int row = s.executeUpdate();
	      System.out.println("Password update successful for "+username);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    
	    return false;
  }
  
  public boolean updateUserRole(String username, String role) {

	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET role = ? "
	    				+ "WHERE username = ?";
	
	    Connection c = null;
	    PreparedStatement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.prepareStatement(sql);
	      s.setString(1, role);
	      s.setString(2, username);
	      int row = s.executeUpdate();
	      System.out.println("Role update successful for "+username+" New role = "+role);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    return false;
  }

}

