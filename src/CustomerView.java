import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:54 PM
 */
public class CustomerView
{
	private CustomerController customerController;
	
	public CustomerView(CustomerController customerController)
	{
		this.customerController = customerController;
		System.out.println("Welcome Customer ");
		
	}
	
	
	public void searchStockNumber()
	{
		
	}
	
	
	public void addItemToCart()
	{
		
	}
	
	public void displayCart()
	{
		ResultSet rs = customerController.getCartItems();
		System.out.println("Currently in Cart:");
		try
		{
			while(rs.next())
			{
				System.out.println(rs.getString("stock_number") + "x" + rs.getInt("amount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
