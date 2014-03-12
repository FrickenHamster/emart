import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/10/14
 * Time: 7:21 PM
 */
public class CustomerController
{

	private Connection connection;
	private CustomerModel currentModel;
	private String customerIdentifier;


	public CustomerController(Connection connection, String customerIdentifier)
	{
		this.connection = connection;
		this.customerIdentifier = customerIdentifier;
		
		currentModel = new CustomerModel(connection);
		currentModel.load(customerIdentifier);
		
	}

	public ResultSet searchStockNumber(String stockNumber)
	{
		
		try
		{
			String searchString = "select * from martitem where stock_number = ?";
			PreparedStatement stmt = connection.prepareStatement(searchString);
			stmt.setString(1, stockNumber);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void addToCart(String stockNumber, int amount)
	{
		
		CartItemModel cartModel = new CartItemModel(connection);
		if (cartModel.load(customerIdentifier, stockNumber))
		{
			cartModel.setAmount(cartModel.getAmount() + amount);
			cartModel.update();
		}
		else
		{
			cartModel.setAll(customerIdentifier, stockNumber,amount);
			cartModel.insert();
		}
	}
	
	public ResultSet getCartItems()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from cartitem where trim(cid) = ?");
			stmt.setString(1, customerIdentifier);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getStockNumberSearch(String stockNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem where trim(stock_number) = ?");
			stmt.setString(1, stockNumber);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getCategorySearch(String category)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem where trim(category) = ?");
			stmt.setString(1, category);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getCustomerIdentifier()
	{
		return customerIdentifier;
	}
}
