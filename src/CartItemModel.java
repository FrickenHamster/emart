import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:54 PM
 */
public class CartItemModel
{
	private Connection connection;
	private String customerIdentifier;
	private String stockNumber;
	private int amount;

	public CartItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String customerIdentifier, String stockNumber, int amount)
	{
		this.customerIdentifier = customerIdentifier;
		this.stockNumber = stockNumber;
		this.amount = amount;
	}
	
	public void insert(String customerIdentifier, String stockNumber, int amount)
	{
		setAll(customerIdentifier, stockNumber, amount);
		insert();
	}
	
	public void insert()
	{
		try
		{
			String insertString = "insert into cartitem values(?, ?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setString(1, customerIdentifier);
			insStmt.setString(2, stockNumber);
			insStmt.setInt(3, amount);
			insStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
