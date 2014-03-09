
import oracle.jdbc.*;

import java.sql.*;

public class Main
{
	
	public static String hostAddress = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	public static String user = "aayan";
	public static String pass = "5608120";
	public static Connection EMART_CONNECTION;
	
	
	public static void main(String[] args) throws SQLException
	{
		EMartTableGenesis tableGen;
		try
		{
			DriverManager.registerDriver(new OracleDriver());
			
			connectEMart();
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (args.length > 0 && args[0].equals( "clear"))
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.clearTables();
		}
		else
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.createTables();
		}

	}
	
	public static void connectEMart()
	{

		try
		{
			EMART_CONNECTION = DriverManager.getConnection(hostAddress, user, pass);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void printAll() throws SQLException
	{
		
	}
	
}
