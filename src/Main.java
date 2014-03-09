
import oracle.jdbc.*;

import java.sql.*;

public class Main
{
	
	public static String hostAddress = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	public static String user = "aayan";
	public static String pass = "5608120";
	public static Connection EMART_CONNECTION;
	public static Connection EDEPOT_CONNECTION;
	
	
	public static void main(String[] args) throws SQLException
	{
		EMartTableGenesis tableGen;
		EDepotTableGen depotGen;
		try
		{
			DriverManager.registerDriver(new OracleDriver());
			
			connectEMart();
			connectEDepot();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (args.length > 0 && args[0].equals( "clear"))
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.clearTables();
			depotGen = new EDepotTableGen(EDEPOT_CONNECTION);
			depotGen.clearTables();
			EMART_CONNECTION.close();
			EDEPOT_CONNECTION.close();
		}
		else
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.createTables();
			depotGen = new EDepotTableGen(EMART_CONNECTION);
			depotGen.createTables();
			tableGen.seedValues();
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
	public static void connectEDepot()
	{
		try
		{
			EDEPOT_CONNECTION = DriverManager.getConnection(hostAddress, user, pass);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void printAll() throws SQLException
	{
		
	}
	
}
