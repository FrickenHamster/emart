import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:39 PM
 */
public class OrderedItemModel
{
	private Connection connection;
	private int orderNumber;
	private String stockNumber;
	private double itemTotal;
	private int amount;

	public OrderedItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(int orderNumber, String stockNumber, double itemTotal, int amount)
	{
		this.orderNumber = orderNumber;
		this.stockNumber = stockNumber;
		this.itemTotal = itemTotal;
		this.amount = amount;
	}
	
	public void insert(int orderNumber, String stockNumber, double itemTotal, int amount)
	{
		setAll(orderNumber, stockNumber, itemTotal, amount);
		insert();
	}

	public void insert()
	{
		try
		{
			String insertString = "insert into OrderedItem values(?, ?, ?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setInt(1, orderNumber);
			insStmt.setString(2, stockNumber);
			insStmt.setInt(3, amount);
			insStmt.setDouble(4, itemTotal);
			insStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
}
