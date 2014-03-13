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
	private ArrayList<Double> searchPrices;
	private Scanner scanner;
	
	public CustomerView(CustomerController customerController)
	{
		this.customerController = customerController;
		System.out.println("Welcome Customer " + customerController.getCustomerIdentifier());
		searchResult = new ArrayList<String>();
		searchPrices = new ArrayList<Double>();
		scanner = new Scanner(System.in);
		mainMenu();
	}
	
	public void mainMenu()
	{
		System.out.println("What would you like to do?");
		System.out.println("1:search");
		System.out.println("2:view shopping cart");
		System.out.println("3:Look up order");
		System.out.println("4:exit");
		int input = scanner.nextInt();
		switch (input)
		{
			case 1:
				searchMenu();
				break;
			case 2:
				displayCart();
				System.out.println("Would you like to 1: check out, or 2:remove item, or 0:nothing");
				int in2 = scanner.nextInt();
				if (in2 == 1)
				{
					customerController.fulfillOrder();
				}
				else if (in2 == 2)
				{
					removeItemMenu();
				}
				break;
			case 3:
				System.out.println("Which orderid?");
				int oid = scanner.nextInt();
				ResultSet rs = customerController.getOrderSearch(oid);
				try
				{
					if (rs.next())
					{
						System.out.println("Ordered on " + rs.getTimestamp("tstmp") + "total of " + rs.getDouble("total"));
						System.out.println("Do you want to reorder? 1: yes, 2 :no");
						if (scanner.nextInt() == 1)
						{
							customerController.addFromOrder(oid);
							customerController.fulfillOrder();
						}
					}
					else
					{
						System.out.println("order not found");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				break;
			case 4:
				try
				{
					Main.EMART_CONNECTION.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				System.exit(0);
				break;
		}
		mainMenu();
	}
	
	public void searchMenu()
	{
		System.out.println("Search by what?");
		System.out.println("1:Stock Number");
		System.out.println("2:Category");
		System.out.println("3:Manufactuer");
		System.out.println("4:Model Number");
		System.out.println("5:Description");
		System.out.println("6:Accessory");
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
			case 5:
				System.out.println("input Attribute");
				scanner.nextLine();
				String att = scanner.nextLine();
				System.out.println(att);
				System.out.println("input Value");
				String val = scanner.nextLine();
				searchDescription(att, val);
				break;
			case 6:
				System.out.println("Input stock Number");
				searchAccessory(scanner.next());
		}
		if (searchResult.size() > 0)
		{
			System.out.println("Add which item to cart? 0 for nope");
			int nn = scanner.nextInt();
			if (nn!= 0)
			{
				System.out.println("How many?");
				addItemToCart(nn, scanner.nextInt());
			}
		}
		mainMenu();
	}
	
	
	public void searchStockNumber(String stockNumber)
	{
		ResultSet rs = customerController.searchStockNumber(stockNumber);
		searchResult.clear();
		searchPrices.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				searchPrices.add(rs.getDouble("price"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void removeItemMenu()
	{
		System.out.println("Which stock number to remove?");
		String input = scanner.next();
		customerController.deleteCartItem(input);
	}

	public void searchCategory(String category)
	{
		ResultSet rs = customerController.getCategorySearch(category);
		searchResult.clear();
		searchPrices.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				searchPrices.add(rs.getDouble("price"));
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
		searchPrices.clear();
		try
		{
			int nn = 1;
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				searchPrices.add(rs.getDouble("price"));
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
		searchPrices.clear();
		try
		{
			int nn = 1;
			System.out.println("search result:");
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				searchPrices.add(rs.getDouble("price"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void searchDescription(String description, String value)
	{
		ResultSet rs = customerController.getDescriptionSearch(description, value);
		searchResult.clear();
		searchPrices.clear();
		try
		{
			int nn = 1;
			System.out.println("search result:");
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("stock_number"));
				searchPrices.add(rs.getDouble("price"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void searchAccessory(String stockNumber)
	{
		ResultSet rs = customerController.getAccessorySearch(stockNumber);
		searchResult.clear();
		searchPrices.clear();
		try
		{
			int nn = 1;
			System.out.println("search result:");
			while(rs.next())
			{
				System.out.println(nn + ":  " +rs.getString( "parent_stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				searchResult.add(rs.getString("parent_stock_number"));
				searchPrices.add(rs.getDouble("price"));
				nn++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addItemToCart(int nn, int amount)
	{
		if (nn > searchResult.size())
		{
			System.out.println("Invalid selection");
			return;
		}
		
		customerController.addToCart(searchResult.get(nn - 1), amount,searchPrices.get(nn - 1) );
		
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
