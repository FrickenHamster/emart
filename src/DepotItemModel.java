import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;

/**
 * Created by jessie on 3/8/14.
 */
public class DepotItemModel
{
	private Connection connection;
	private String stockNumber;
	private int quantity;
	private int maxStock;
	private int minStock;
	private int replenish;
	private String location;
	private String mName;
	private String modelNumber;
	
	public DepotItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String stockNumber, int quantity, int maxStock, int minStock, int replenish, String location, String mName, String modelNumber)
	{
		this.stockNumber = stockNumber;
		this.quantity = quantity;
		this.maxStock = maxStock;
		this.minStock = minStock;
		this.replenish = replenish;
		this.location = location;
		this.mName = mName;
		this.modelNumber = modelNumber;
	}
	
	public void insert()
	{
		Statement statement;
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate("insert into DepotItem " +
					"values(" + "'" + stockNumber + "'" + ","
					 + quantity + "," +  maxStock + "," + minStock + "," + replenish 
					+ "," + "'" + location + "'" + "," + "'" + mName + "'" + "," + "'" + modelNumber + "')");
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	public DepotItemModel(String stockNumber)
	{
		
	}

	public String getStockNumber()
	{
		return stockNumber;
	}

	public void setStockNumber(String stockNumber)
	{
		this.stockNumber = stockNumber;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public int getMaxStock()
	{
		return maxStock;
	}

	public void setMaxStock(int maxStock)
	{
		this.maxStock = maxStock;
	}

	public int getMinStock()
	{
		return minStock;
	}

	public void setMinStock(int minStock)
	{
		this.minStock = minStock;
	}

	public int getReplenish()
	{
		return replenish;
	}

	public void setReplenish(int replenish)
	{
		this.replenish = replenish;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getmName()
	{
		return mName;
	}

	public void setmName(String mName)
	{
		this.mName = mName;
	}

	public String getmodelNumber()
	{
		return modelNumber;
	}

	public void setmodelNumber(String modelNumber)
	{
		this.modelNumber = modelNumber;
	}
	
	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from DepotItem" );
			ResultSet rs = stmt.executeQuery();
			System.out.println("Depot Items:");
			while (rs.next())
			{
				System.out.println(rs.getString("stock_number") + "|" + rs.getString("quantity") + 
					"|" + rs.getString("max_stock")+ "|" + rs.getString("min_stock") + "|" + 
					rs.getString("replenish") + "|" + rs.getString("location") +"|" + rs.getString("mname") 
					+ "|" + rs.getString("model_number"));
				
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
