import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:18 PM
 */
public class CustomerModel
{
	private Connection connection;
	
	private String customerIdentifier;
	private String password;
	private String name;
	private String email;
	private String address;
	private String status;
	private String manager;
	
	
	
	public CustomerModel(Connection connection)
	{
		this.connection = connection;
	}

	public CustomerModel(Connection connection, String customerIdentifier, String password, String name, String email, String address, String status, String manager)
	{
		this.connection = connection;
		this.customerIdentifier = customerIdentifier;
		this.password = password;
		this.name = name;
		this.email = email;
		this.address = address;
		this.status = status;
		this.manager = manager;
	}

	public void setAll(String customerIdentifier, String password, String name, String email, String address, String status, String manager)
	{
		this.customerIdentifier = customerIdentifier;
		this.password = password;
		this.name = name;
		this.email = email;
		this.address = address;
		this.status = status;
		this.manager = manager;
	}

	public void load(String customerIdentifier)
	{
		this.customerIdentifier = customerIdentifier;
		Statement statement = null;
		try
		{
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select password, customer_name, email, address, status, is_manager," +
					"from customer c," +
					"where c.cid =" + customerIdentifier + ";");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void insert(String customerIdentifier, String password, String name, String email, String address, String status, String manager)
	{
		setAll(customerIdentifier,password,name,email,address,status,manager);
		insert();
	}
	
	public void insert()
	{
		try
		{
			String insString = "insert into customer values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(insString);
			stmt.setString(1, customerIdentifier);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, status);
			stmt.setString(7, manager);
			stmt.executeUpdate();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from customer");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Customers:");
			while (rs.next())
			{
				System.out.println(rs.getString("cid") + " | " + rs.getString("password") + " | " + rs.getString("customer_name") + " | " + rs.getString("email") + " | " + rs.getString("address") + " | " + rs.getString("status") + " | " + rs.getString("is_manager"));
				PreparedStatement cartStmt = Main.EMART_CONNECTION.prepareStatement("select * from cartitem where cid = ?");
				System.out.println("Cart Items:");
				cartStmt.setString(1, rs.getString("cid"));
				ResultSet cartrs = cartStmt.executeQuery();
				while (cartrs.next())
				{
					System.out.println(cartrs.getString("stock_number") + " x" + cartrs.getInt("amount"));
				}
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
}
