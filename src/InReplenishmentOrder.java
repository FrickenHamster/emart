import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jessie on 3/10/14.
 */
public class InReplenishmentOrder
{
	Connection connection;
	int orderid;
	String stockNumber;
	int amount;
	
	public InReplenishmentOrder(Connection connection)
	{
		this.connection = connection;
	}
	public void setAll(int orderid, String stockNumber, int amount)
	{
		this.orderid = orderid;
		this.stockNumber = stockNumber;
		this.amount = amount;
	}
	
	public void insert(int orderid, String stockNumber, int amount)
	{
		setAll(orderid, stockNumber, amount);
		insert();
	}
	public void insert()
	{
		try
		{
			String insertString = "insert into InReplenishmentOrder values (?,?,?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setInt(1, orderid);
			insStmt.setString(2, stockNumber);
			insStmt.setInt(3, amount);
			insStmt.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
/*	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * from InReplenishmentOrder");
			ResultSet rs = stmt.executeQuery();
			System.out.println("In Replenishment Order:");
			while(rs.next())
			{
				System.out.println(rs.getInt("order_id") + "|" + rs.getString("stock_number") + "|"
					+ rs.getInt("amount"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}*/
}
