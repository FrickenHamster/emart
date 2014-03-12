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
	public void insert(String stockNumber, int quantity, int maxStock, int minStock, int replenish, String location, String mName, String modelNumber)
	{
		setAll(stockNumber, quantity, maxStock, minStock, replenish, location, mName, modelNumber);
		insert();
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
	
	public void update()
	{
		try{
			PreparedStatement stmt = connection.prepareStatement("update DepotItem set stock_number = ?, quantity = ?, max_stock = ?, min_stock = ?, replenish = ?, location = ?, mname = ?, model_number = ? where stock_number = ?");
			stmt.setString(1, stockNumber);
			stmt.setInt(2, quantity);
			System.out.println(quantity);
			stmt.setInt(3, maxStock);
			stmt.setInt(4, minStock);
			stmt.setInt(5, replenish);
			stmt.setString(6, location);
			stmt.setString(7,mName);
			stmt.setString(8,modelNumber);
			stmt.setString(9, stockNumber);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	public boolean load(String stockNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from DepotItem where stock_number = ?");
			stmt.setString(1, stockNumber);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
			{
				return false;
			}
			this.stockNumber = rs.getString("stock_number");
			this.quantity = rs.getInt("quantity");
			this.maxStock = rs.getInt("max_stock");
			this.minStock = rs.getInt("min_stock");
			this.replenish = rs.getInt("replenish");
			this.location = rs.getString("location");
			this.mName = rs.getString("mname");
			this.modelNumber = rs.getString("model_number");
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
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
