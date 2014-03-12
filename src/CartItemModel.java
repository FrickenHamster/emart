import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private double price;

	public CartItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String customerIdentifier, String stockNumber, int amount, double price)
	{
		this.customerIdentifier = customerIdentifier;
		this.stockNumber = stockNumber;
		this.amount = amount;
		this.price = price;
	}
	
	public void insert(String customerIdentifier, String stockNumber, int amount, double price)
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
	
	public void update()
	{
		try
		{
			String updateString = "update cartitem set amount = ? where trim(cid) = trim(?) and stock_number = ?";
			PreparedStatement stmt = connection.prepareStatement(updateString);
			stmt.setString(3, stockNumber);
			stmt.setInt(1, amount);
			System.out.println(amount + "," + stockNumber + customerIdentifier);
			stmt.setString(2, customerIdentifier);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean load(String customerIdentifier, String stockNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from cartitem where trim(cid) = ? and stock_number = ?");
			stmt.setString(1, customerIdentifier);
			stmt.setString(2, stockNumber);
			ResultSet rs = stmt.executeQuery();
			if (!rs.next())
			{
				return false;
			}
			this.customerIdentifier = rs.getString("cid");
			this.stockNumber = rs.getString("stock_number");
			this.amount = rs.getInt("amount");
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getAmount()
	{
		return amount;
	}
}
