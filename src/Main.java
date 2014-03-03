
import oracle.jdbc.*;

import java.sql.*;

public class Main
{
	
	
	public static String hostAddress = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	public static String user = "aayan";
	public static String pass = "5608120";
	
	
	public static void main(String[] args) throws SQLException
	{
		try
		{
			DriverManager.registerDriver(new OracleDriver());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void printAll() throws SQLException
	{
		Connection connection = DriverManager.getConnection(hostAddress, user, pass);
		
		Statement stmt = connection.createStatement();
		
	}
	
}
