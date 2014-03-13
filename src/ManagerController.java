import java.sql.*;
import java.util.Calendar;

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
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void changeCustomerStatus(String cid, String status)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("update customer set status = ? where trim(cid) = ?");
			stmt.setString(1, status);
			stmt.setString(2, cid);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public AmountPrice getSaleItem(String stock_number)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select sum(o.amount) as atot, sum(o.item_total) as itot from ordereditem o join sale s on o.order_id = s.order_id where o.stock_number = ?" +
					" and (s.order_month = ?) and (s.order_year = ?) ");
			Calendar cal = Calendar.getInstance();
			stmt.setString(1, stock_number);
			stmt.setInt(2, cal.get(Calendar.MONTH));
			stmt.setInt(3, cal.get(Calendar.YEAR));
			ResultSet rs = stmt.executeQuery();
			rs.next();
			AmountPrice ap = new AmountPrice(rs.getInt("atot"), rs.getDouble("itot"));
			return ap;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
