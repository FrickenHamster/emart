import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/8/14
 * Time: 7:42 PM
 */
public class DiscountModel
{
	Connection connection;
	
	
	private String status;
	private double percent;

	public DiscountModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String status, double percent)
	{
		this.status = status;
		this.percent = percent;
	}
	
	public void insert(String status, double percent)
	{
		setAll(status, percent);
		insert();
	}
	
	public void insert()
	{
		
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into discount " +
					"values(" + "'" + status + "'" + "," + percent + ")");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
