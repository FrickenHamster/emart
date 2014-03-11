import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/10/14
 * Time: 7:21 PM
 */
public class CustomerController
{

	private Connection connection;

	public CustomerController(Connection connection)
	{
		this.connection = connection;
	}

	public ResultSet searchStockNumber(String stockNumber)
	{
		
		try
		{
			String searchString = "select * from martitem where stock_number = ?";
			PreparedStatement stmt = connection.prepareStatement(searchString);
			stmt.setString(1, stockNumber);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
