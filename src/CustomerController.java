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

	public CustomerController(Connection connection)
	{
		this.connection = connection;
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
		if (cartModel.load(currentModel.getCustomerIdentifier(), stockNumber))
		{
			cartModel.setAmount(cartModel.getAmount() + amount);
			cartModel.update();
		}
		else
		{
			cartModel.setAll(currentModel.getCustomerIdentifier(), stockNumber,amount);
			cartModel.insert();
		}
	}
	
	public ResultSet getCartItems()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from cartitem where cid = ?");
			stmt.setString(1, currentModel.getCustomerIdentifier());
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
}
