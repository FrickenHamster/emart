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
		{
			String createString = "create table mart_item(" +
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
				if (!e.getMessage().startsWith(" ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table mart_item already exists");
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
				if (!e.getMessage().startsWith(" ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table description already exists");
				}
				else
					e.printStackTrace();
			}
		}
	}
	
	public void clearTables()
	{
		dropTable("mart_item");
		dropTable("description");
	}
	private void dropTable(String tableName)
	{
		
		try
		{
			String sql = "drop table " + tableName;
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("table " + tableName + " dropped from database");

	}
	
	
}
