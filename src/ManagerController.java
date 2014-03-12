import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/10/14
 * Time: 9:27 PM
 */
public class ManagerController
{
	private Connection connection;

	public ManagerController(Connection connection)
	{
		this.connection = connection;
	}

	public void changePrice(String stockNumber, double price)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("update martitem set price = ? where stock_number = ?");
			stmt.setDouble(1, price);
			stmt.setString(2, stockNumber);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public AmountPrice getSaleItem(String stock_number)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select sum(amount) as atot, sum(amount) as ttot from ordereditem where stock_number = ?");
			stmt.setString(1, stock_number);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return new AmountPrice(rs.getInt("atot"), rs.getDouble("ttot"));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
