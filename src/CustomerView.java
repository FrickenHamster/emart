import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:54 PM
 */
public class CustomerView
{
	private CustomerController customerController;
	private ArrayList<String> searchResult;
	
	public CustomerView(CustomerController customerController)
	{
		this.customerController = customerController;
		System.out.println("Welcome Customer " + customerController.getCustomerIdentifier());
		searchResult = new ArrayList<String>();
	}
	
	
	public void searchStockNumber(String stockNumber)
	{
		ResultSet rs = customerController.searchStockNumber(stockNumber);
		searchResult.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void searchCategory(String category)
	{
		ResultSet rs = customerController.getCategorySearch(category);
		searchResult.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addItemToCart(int nn)
	{
		if (nn > searchResult.size())
		{
			System.out.println("Invalid selection");
			return;
		}
		
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
