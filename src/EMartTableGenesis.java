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
		String createString = "create table mart_item(" +
				"stock_number char(20)," +
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


	private void createTable(String tableName, String[] contents)
	{
		String createString = "create table " + tableName + " (";
		for (String content : contents)
		{

		}
	}
}
