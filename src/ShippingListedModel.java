import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jessie on 3/8/14.
 */
public class ShippingListedModel
{
	private Connection connection;
	
	private String stockNumber;
	private int shipid;
	private int amount;
	
	public ShippingListedModel(Connection connection)
	{
		this.connection = connection;
	}
	
	public void setAll(String stockNumber, int shipid, int amount)
	{
		this.stockNumber = stockNumber;
		this.shipid = shipid;
		this.amount = amount;
	}
	
	public void insert(String stockNumber, int shipid, int amount)
	{
		setAll(stockNumber, shipid, amount);
		insert();
	}
	public void insert()		
	{
		try
		{
			String insertString = "insert into ShippingListed values (?,?,?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setInt(1, shipid);
			insStmt.setString(2, stockNumber);
			
			insStmt.setInt(3, amount);
			insStmt.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from ShippingListed");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Mart Items:");
			while (rs.next())
			{
				System.out.println(rs.getString("stock_number") + "|" + rs.getInt("shipid")
					+ "|" + rs.getInt("amount"));
				System.out.println("Shipping Notice:");
				PreparedStatement notice = Main.EMART_CONNECTION.prepareStatement("Select stock_number, shipid, amount from ShippingListed where shipid = ?");
				notice.setInt(1, rs.getInt("shipid"));
				ResultSet nrs = notice.executeQuery();
				while(nrs.next())
				{
					System.out.println(nrs.getInt("ship_id") + "|" + nrs.getString("company_name"));
				}
				
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}*/


	public String getStockNumber()
	{
		return stockNumber;
	}

	public void setStockNumber(String stockNumber)
	{
		this.stockNumber = stockNumber;
	}

	public int getShipid()
	{
		return shipid;
	}

	public void setShipid(int shipid)
	{
		this.shipid = shipid;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
