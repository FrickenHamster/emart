import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jessie on 3/8/14.
 */
public class ReplenishmentOrderModel
{
	private int orderid;
	private String mname;
	
	public ReplenishmentOrderModel()
	{
		
	}
	
	public ReplenishmentOrderModel(int orderid)
	{
		
	}

	public int getOrderid()
	{
		return orderid;
	}

	public void setOrderid(int orderid)
	{
		this.orderid = orderid;
	}

	public String getMname()
	{
		return mname;
	}

	public void setMname(String mname)
	{
		this.mname = mname;
	}
	
	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from ReplenishmentOrder");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Replenishment Order:");
			while (rs.next())
			{
				System.out.println(rs.getString("order_id") + "|" + rs.getString("mname"));
				System.out.println("in order:");
				PreparedStatement ordstmt = Main.EMART_CONNECTION.prepareStatement("select order_id, " +
						"stock_number, amount" + "from InReplenishmentOrder where order_id = ?");
				ordstmt.setString(1, rs.getString("order_id"));
				ResultSet ordrs = ordstmt.executeQuery();
				while(ordrs.next())
				{
					System.out.println(ordrs.getString("order_id") + "|" + ordrs.getString("stock_number") 
							+ "," + ordrs.getString("amount"));
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
