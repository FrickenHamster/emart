import java.sql.*;
import java.util.Date;

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
		} else
		{
			cartModel.setAll(customerIdentifier, stockNumber, amount);
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

	public int getNextOrderId()
	{
		try
		{

			PreparedStatement stmt = connection.prepareStatement("select max(order_id) as maxid from sale");
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				return rs.getInt("maxid") + 1;
			} else
				return 0;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public void fulfillOrder()
	{
		
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select sum(i.price * c.amount) as total from martitem i join cartitem c on i.stock_number = c.stock_number where trim(c.cid) = ?");
			stmt.setString(1, customerIdentifier);
			ResultSet cartresult = stmt.executeQuery();
			cartresult.next();
			double total = cartresult.getDouble("total");
			
			SaleModel saleModel = new SaleModel(connection);
			int ordnum = getNextOrderId();
			saleModel.setAll(ordnum, customerIdentifier, total, new Timestamp(new Date().getTime()));
			saleModel.insert();
			cartresult = getCartItems();
			OrderedItemModel orderModel = new OrderedItemModel(connection);
			while (cartresult.next())
			{
				PreparedStatement pstmt = connection.prepareStatement("select (price * ?) as total from martitem where stock_number = ?");
				pstmt.setInt(1, cartresult.getInt("amount"));
				
				pstmt.setString(2, cartresult.getString("stock_number"));
				ResultSet prs = pstmt.executeQuery();
				prs.next();
				
				
				
				orderModel.setAll(ordnum, cartresult.getString("stock_number"), prs.getDouble("total"), cartresult.getInt("amount"));
				orderModel.insert();
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}


	}

}
