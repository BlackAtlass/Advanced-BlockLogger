package com.dev.aaaa.plugins.blocklogger.model.storage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mbserver.api.game.Location;
import com.mbserver.api.game.Player;

public class Database {
	private Connection con;
	private Statement st;
	
	public boolean init(){
		File db = new File("plugins/ADV_BlockLogger/database.sqlite");
		boolean exists = true;
		if(!db.exists())
			exists = false;
		
		new org.sqlite.JDBC();
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + db.getCanonicalPath());
			st = con.createStatement();
			con.setAutoCommit(false);
			if(!exists)
			{
				String create = "CREATE TABLE Blocks (ID TEXT PRIMARY KEY,LOGIN TEXT,DISPLAY TEXT)";
				
				st.executeUpdate(create);
				con.commit();
			}
			con.prepareStatement("PRAGMA user_version = 1;").execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getStatement(Location loc,Player p){
		String command;
		command = String.format("INSERT OR REPLACE INTO Blocks (ID,LOGIN,DISPLAY) VALUES ('%s','%s','%s')",
				getKey(loc),p.getLoginName(),p.getDisplayName());
		return command;
	}
	
	public void commit(){
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update(Location loc,Player p){
		String command = getStatement(loc, p);
		try {
			st.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getKey(Location loc){
		StringBuilder build = new StringBuilder();
		build.append(loc.getBlockX());
		build.append(loc.getBlockY());
		build.append(loc.getBlockZ());
		build.trimToSize();
		return build.toString();
	}
	
	public String[] read(Location loc){
		String names[] = new String[2];
		try {
			con.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ResultSet set = st.executeQuery(String.format("SELECT * FROM Blocks WHERE ID='%s';",getKey(loc)));
			boolean isGood = set.next();
			
			if(isGood)
			{
				names[0] = set.getString("LOGIN");
				names[1] = set.getString("DISPLAY");
			}
			
			else
				names = null;
			set.close();
			return names;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}


