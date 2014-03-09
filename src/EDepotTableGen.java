import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by jessie on 3/8/14.
 */
public class EDepotTableGen
{
	private Connection connection;
	public EDepotTableGen(Connection connection)
	{
		this.connection = connection;
	}
	public void createTables()
	{
		String createString = "create table DepotItem(" +
				"stock_number char(7)," +
				"quantity integer not null," +
				"max_stock integer not null," +
				"min_stock integer not null," +
				"replenish integer not null," +
				"location char(20) not null," +
				"primary key (stock_number))";
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(createString);
		}
		catch (SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
			{
				System.out.println("table DepotItem already exists");
			}
			else 
				e.printStackTrace();
		}
	}
	
	{
		String createString = "create table ShippingNotice" +
				"shipid integer," +
				"company_name char(20)" +
				"primary key (shipid))";
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(createString);
		} catch (SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
			{
				System.out.println("table ShippingNotice already exists");
			}
			else
				e.printStackTrace();
		
		}
	}
	{
		String createString = "create table ReplenishmentOrder(" +
				"orderid integer," +
				"mname char(20)" +
				"primary key (orderid)," +
				"foreign key (mname) references DepotItem (mname)";
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(createString);
		}
		catch(SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
			{
				System.out.println("table ReplenishmentOrder already exists");
			}
			else
				e.printStackTrace();
		}
	}
	
}
