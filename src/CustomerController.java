import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
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

	public void addToCart(String stockNumber, int amount, double price)
	{

		CartItemModel cartModel = new CartItemModel(connection);
		if (cartModel.load(customerIdentifier, stockNumber))
		{
			cartModel.setAmount(cartModel.getAmount() + amount);

			cartModel.update();
		} else
		{
			cartModel.setAll(customerIdentifier, stockNumber, amount, price);
			cartModel.insert();
		}
	}

	public void deleteCartItem(String stockNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("delete from cartitem where trim(cid) = ? and stock_number = ?");
			stmt.setString(1, customerIdentifier);
			stmt.setString(2, stockNumber);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void clearCart()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("delete from cartitem where trim(cid) = ?");
			stmt.setString(1, customerIdentifier);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
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

	public ResultSet getDescriptionSearch(String attribute, String value)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem i " +
					"join description d on d.stock_number = i.stock_number " +
					"where trim(d.attribute) = trim(?) " +
					"and trim(d.attribute_value) = trim(?)");
			stmt.setString(1, attribute);
			stmt.setString(2, value);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getAccessorySearch(String stockNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem i " +
					"join accessory a on i.stock_number = a.child_stock_number " +
					"where trim(i.stock_number) = trim(?) ");
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

	public ResultSet getManufacturerSearch(String manufacturer)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem where trim(manufacturer) = trim(?)");
			stmt.setString(1, manufacturer);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getModelNumberSearch(String modelNumber)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem where trim(model_number) = trim(?)");
			stmt.setString(1, modelNumber);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getOrderSearch(int orderId)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from sale where trim(order_id) = trim(?)");
			stmt.setInt(1, orderId);
			return stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void addFromOrder(int orderId)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from ordereditem where trim(order_id) = trim(?)");
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				MartItemModel item = new MartItemModel(connection);
				item.load(rs.getString("stock_number"));
				addToCart(rs.getString("stock_number"), rs.getInt("amount"), item.getPrice());
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
			PreparedStatement stmt = connection.prepareStatement("select sum(i.price * c.amount * d.percent) as total " +
					" from martitem i " +
					"join cartitem c on i.stock_number = c.stock_number " +
					"join discount d on d.status = ? " +
					"where trim(c.cid) = ? ");
			stmt.setString(1, currentModel.getStatus());
			stmt.setString(2, customerIdentifier);
			ResultSet cartresult = stmt.executeQuery();
			cartresult.next();
			double total = cartresult.getDouble("total");
			if (total <= 100)
			{
				stmt = connection.prepareStatement("select percent from discount where status = 'Shipping'");
				ResultSet srs = stmt.executeQuery();
				srs.next();
				total *= srs.getDouble("percent");
			}
			SaleModel saleModel = new SaleModel(connection);
			int ordnum = getNextOrderId();
			Calendar cal = Calendar.getInstance();
			saleModel.setAll(ordnum, customerIdentifier, total, new Timestamp(new Date().getTime()), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
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
			clearCart();
			
			checkStatus();
			WarehouseController wcon = new WarehouseController(connection);
			wcon.fillCustomerOrder(ordnum);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}


	public void checkStatus()
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select sum(s1.total) as total from sale s1 " +
					"where trim(s1.cid) = trim(?) " +
					"and (select count (s2.order_id) " +
					"from sale s2 " +
					"where s2.tstmp > s1.tstmp " +
					"and s1.cid = s2.cid ) < 3 ");

			stmt.setString(1, customerIdentifier);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			double sum = rs.getDouble("total");
			if (sum < 100)
			{
				setStatus("Green");
			} else if (sum < 500)
			{
				setStatus("Silver");
			} else
			{
				setStatus("Gold");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void setStatus(String status)
	{
		currentModel.setStatus(status);
		currentModel.update();
	}

}
