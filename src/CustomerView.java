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
	private Scanner scanner;
	
	public CustomerView(CustomerController customerController)
	{
		this.customerController = customerController;
		System.out.println("Welcome Customer " + customerController.getCustomerIdentifier());
		searchResult = new ArrayList<String>();
		scanner = new Scanner(System.in);
		mainMenu();
	}
	
	public void mainMenu()
	{
		System.out.println("What would you like to do?");
		System.out.println("1:search");
		System.out.println("2:view shopping cart");
		int input = scanner.nextInt();
		switch (input)
		{
			case 1:
				searchMenu();
				break;
			case 2:
				
				break;
		}
	}
	
	public void searchMenu()
	{
		System.out.println("Search by what?");
		System.out.println("1:Stock Number");
		System.out.println("2:Category");
		System.out.println("3:Manufactuer");
		System.out.println("4:Model Number");
		int input = scanner.nextInt();
		switch (input)
		{
			case 1:
				System.out.println("Input Stock Number:");
				String str = scanner.next();
				searchStockNumber(str);
				break;
			
			case 2:
				System.out.println("input Category");
				searchCategory(scanner.next());
				break;
			case 3:
				System.out.println("input Manufacturer");
				searchManufacturer(scanner.next());
				break;
			case 4:
				System.out.println("input Model Number");
				searchModelNumber(scanner.next());
				break;
		}
		mainMenu();
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
				searchResult.add(rs.getString("stock_number"));
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
				searchResult.add(rs.getString("stock_number"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void searchManufacturer(String manufacturer)
	{
		ResultSet rs = customerController.getManufacturerSearch(manufacturer);
		searchResult.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void searchModelNumber(String modelNumber)
	{
		ResultSet rs = customerController.getModelNumberSearch(modelNumber);
		searchResult.clear();
		try
		{
			int nn = 1;
			System.out.println("search result:");
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
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
