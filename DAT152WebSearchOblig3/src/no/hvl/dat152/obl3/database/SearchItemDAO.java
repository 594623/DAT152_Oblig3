package no.hvl.dat152.obl3.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchItemDAO {

  public List<SearchItem> getSearchHistoryLastFive() {
    String sql = "SELECT * FROM SecOblig.History ORDER BY datetime DESC";
    // LIMIT 5
    // Derby lacks LIMIT
    return getSearchItemList(sql,5);
  }

  public List<SearchItem> getSearchHistoryForUser(String username) {
	    String sql = "SELECT * FROM SecOblig.History " 
	        + "WHERE username = ?"
	        + " ORDER BY datetime DESC";
	    //  LIMIT 50
	    // Derby lacks LIMIT
	    return getSearchItemList(sql,50,username);
	  }
  
  public List<SearchItem> getSearchHistoryForUser(String username, String sortkey) {
    String sql = "SELECT * FROM SecOblig.History " 
        + "WHERE username = ?"
        + " ORDER BY "+sortkey+" ASC";
    //  LIMIT 50
    // Derby lacks LIMIT
    return getSearchItemList(sql,50,username);
  }

  private List<SearchItem> getSearchItemList(String sql, Integer limit, String... statementStrings) {

    List<SearchItem> result = new ArrayList<SearchItem>();

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      for (int i = 0; i < statementStrings.length; i++) {
    	  s.setString(i + 1, statementStrings[i]);
      }
      if (limit > 0) s.setMaxRows(limit);
      r = s.executeQuery();

      while (r.next()) {
        SearchItem item = new SearchItem(
            r.getTimestamp("datetime"),
            r.getString("username"),
            r.getString("searchkey")
            );
        result.add(item);
      }

    } catch (Exception e) {
    	e.printStackTrace();
      //System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }

    return result;
  }

  public void saveSearch(SearchItem search) {
    
    String sql = "INSERT INTO SecOblig.History VALUES ("
    		+ "?, ?, ?)";

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      s.setTimestamp(1, search.getDatetime());
      s.setString(2, search.getUsername());
      s.setString(3, search.getSearchkey());
      s.executeUpdate();

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
  }

}
