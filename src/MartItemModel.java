import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 3/3/14
 * Time: 11:31 AM
  */
public class MartItemModel
{
	private Connection connection;
	
	private int stockNumber;
	private String category;
	private double price;
	private int warranty;
	private String mName;
	private String mNumber;

	public MartItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public MartItemModel(Connection connection, int stockNumber, String category, double price, int warranty, String mName, String mNumber)
	{
		this.connection = connection;
		this.stockNumber = stockNumber;
		this.category = category;
		this.price = price;
		this.warranty = warranty;
		this.mName = mName;
		this.mNumber = mNumber;
	}
	
	
	public void insert()
	{
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into martitem " +
					"values(" + "'" + stockNumber + "'" + "," + warranty + "," + price + "," + "'" + category + "'" + ")");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}
