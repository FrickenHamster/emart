import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jessie on 3/8/14.
 */
public class ShippingNoticeModel
{
	private Connection connection;
	private int shipid;
	private String companyName;

	public ShippingNoticeModel()
	{
		
	}
	public ShippingNoticeModel(Connection connection)
	{
		this.connection = connection;
	}
	
	public void setAll(int shipid, String companyName )
	{
		this.shipid = shipid;
		this.companyName = companyName;
	}
	
	public void insert(int shipid, String companyName )
	{
		setAll(shipid, companyName);
		insert();
	}
	public void insert()
	{
		try
		{
			String insertString = "insert into ShippingNotice values (?,?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setInt(1, shipid);
			insStmt.setString(2, companyName);
			insStmt.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public int getShipid()
	{
		return shipid;
	}

	public void setShipid(int shipID)
	{
		this.shipid = shipID;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}
	
	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from ShippingNotice");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Shippin' Notice:");
			while (rs.next())
			{
				System.out.println(rs.getInt("ship_id") + "|" + rs.getString("company_name"));
				System.out.println("Shippin' Listed:");
				PreparedStatement lstmt = Main.EMART_CONNECTION.prepareStatement("Select ship_id, stock_number, amount from ShippingListed where ship_id = ?");
				lstmt.setInt(1, rs.getInt("ship_id"));
				ResultSet lrs = lstmt.executeQuery();
				while(lrs.next())
				{
					System.out.println(lrs.getInt("ship_id") + "|" + lrs.getString("stock_number") + "|" + lrs.getInt("amount"));
				}

			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
