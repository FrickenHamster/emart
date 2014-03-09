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
				"ship_id integer," +
				"company_name char(20)" +
				"primary key (ship_id))";
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
		String createString = "create table ShippingListed" +
				"ship_id integer," +
				"stock_number char(20)," +
				"amount integer not null," +
				"primary key (ship_id, stock_number)," +
				"foreign key (ship_id) references ShippingNotice (ship_id)," +
				"foreign key (stock_number) references Items (stock_number)";
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(createString);
		} catch (SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
			{
				System.out.println("table ShippingListed already exists");
			}
			else
				e.printStackTrace();

		}
	}
	
	{
		String createString = "create table ReplenishmentOrder(" +
				"order_id integer," +
				"mname char(20)" +
				"primary key (order_id)," +
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
	{
		String createString = "create table InReplenishmentOrder(" +
				"order_id integer," +
				"stock_number char(20)" +
				"amount integer not null" +
				"primary key (order_id, stock_number)," +
				"foreign key (order_id) references DepotItem (order_id)" +
				"foreign key (stock_number) references Items (stock_number))";
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(createString);
		}
		catch(SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
			{
				System.out.println("table InReplenishmentOrder already exists");
			}
			else
				e.printStackTrace();
		}
	}
}
