package utill;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection 
{
	private static final String URL = "jdbc:mysql://localhost:3306/tms_db";
	private static final String user = "root";
	private static final String password = "Saravanan@9";
	
	public static Connection getconnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, user, password);
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		
		return null;
	}
}
