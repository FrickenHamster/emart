
import oracle.jdbc.*;

import java.sql.*;

public class Main
{
	//
    //blahblahblabh
    //blahblahbalbh
    //asdfjadfgkliuwerlknfkshwoeriukdfnkjfgh
	
	/*hi uncle same do you like to do the dancing?
	I like to do the dancing but I have to go to school first
	ooooooooooooooooohhhhhhhh
	 */
	
	
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
		
		while (true)
			System.out.println("every day go to school to be the good boy");
	}
	
}
