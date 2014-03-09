import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/8/14
 * Time: 9:18 PM
 */
public class EMartTableGenesis
{
	private Connection connection;
	public EMartTableGenesis(Connection connection)
	{
		this.connection = connection;
		
	}

	public void createTables()
	{
		// make sure you create the tables that are referenced by foreign constaints before you create the tables with foreign constraints in them.
		{
			String createString = "create table martitem(" +
					"stock_number char(7)," +
					"warranty integer not null," +
					"price real not null," +
					"category char(20)," +
					"primary key (stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Description(" +
					"stock_number char(7)," +
					"attribute char(20)," +
					"attribute_value char(20) not null," +
					"primary key (stock_number, attribute))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Accessory(" +
					"parent_stock_number char(7)," +
					"child_stock_number char(7)," +
					"primary key (parent_stock_number, child_stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Discount(" +
					"status char(20)," +
					"percent real not null," +
					"primary key(status))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Customer(" +
					"cid char(20)," +
					"password char(20)," +
					"customer_name char(20)," +
					"email char(20)," +
					"address char(20)," +
					"status char(20)," +
					"primary key (cid)," +
					"foreign key (status) references discount(status))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table CartItem(" +
					"cid char(20)," +
					"stock_number char(20)," +
					"amount integer not null," +
					"primary key (cid, stock_number)," +
					"foreign key (cid) references customer(cid)," +
					"foreign key (stock_number) references martitem(stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Sales(" +
					"order_number integer," +
					"cid char(20)," +
					"order_date timestamp not null," +
					"primary key(order_number, cid))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}	
		}
		{
			String createString = "create table OrderedItem(" +
					"order_number integer," +
					"total real not null," +
					"order_date timestamp not null," +
					"primary key (order_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sales already exists");
				}
				else
					e.printStackTrace();
			}
		}
		
	}
	
	public void clearTables()
	{
		// When clearing tables, make sure you remove the ones with the foreign constraints first
		dropTable("accessory");
		dropTable("cartitem");
		dropTable("ordereditem");
		dropTable("customer");
		dropTable("martitem");
		dropTable("description");
		
		dropTable("discount");
		dropTable("sales");
		
	}
	private void dropTable(String tableName)
	{
		boolean ee = false;
		try
		{
			String sql = "drop table " + tableName;
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e)
		{
			if (e.getMessage().startsWith("ORA-00942: table or view does not exist"))
				System.out.println(tableName + " does not exist");
			else
				e.printStackTrace();
			ee = true;
		}
		if (!ee)
			System.out.println("table " + tableName + " dropped from database");

	}
	
	
}
