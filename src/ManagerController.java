import java.sql.*;
import java.util.ArrayList;
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
	private ArrayList<String> replenishStockNumbers;
	private ArrayList<Integer> replenishAmount;

	public ManagerController(Connection connection)
	{
		this.connection = connection;
		replenishStockNumbers = new ArrayList<String>();
		replenishAmount = new ArrayList<Integer>();
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
	
	public AmountPrice getSaleCategory(String category)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select sum(o.amount) as atot, sum(o.item_total) as itot from ordereditem o" +
					" join sale s on o.order_id = s.order_id" +
					" join martitem i on i.stock_number = o.stock_number" +
					" where trim(i.category) = ?" +
					" and (s.order_month = ?) and (s.order_year = ?) ");
			Calendar cal = Calendar.getInstance();
			stmt.setString(1, category);
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
	
	public AmountPrice getMostCustomerSales()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select r.cid, r.sums " +
					"from (select s.cid, sum(s.total) as sums " +
					"from sale s " +
					"group by s.cid) r " +
					"where r.sums = (select max(r2.sums) from (select s2.cid, sum(s2.total) as sums " +
					"from sale s2 " +
					"group by s2.cid) r2 )" +
					"");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString("cid") + rs.getDouble("sums"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void addToReplenishmentOrder(String stockNumber, int amount)
	{
		replenishStockNumbers.add(stockNumber);
		replenishAmount.add(amount);
	}
	
	public void deleteNotNeededSale()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("delete from sale s1 " +
					"where (select count (s2.order_id) " +
					"from sale s2 " +
					"where s2.tstmp > s1.tstmp " +
					"and s1.cid = s2.cid ) > 2 ");
			stmt.executeUpdate();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
