package com.project.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoConnection {
	Connection con =null;
	public Connection OpenConection() throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "sys as sysdba";
		String password = "system";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(url,userName,password);
		return con;
		
	}
	
	public void closeConnection (){
		try{
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
