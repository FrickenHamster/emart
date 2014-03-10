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
	
	public void insert()
	{
		Statement statement;
		try
		{
			statement = connection.createStatement();
			//System.out.println("insert into customer " +
			//		"values(" + customerIdentifier + "," + password + "," + name + "," + email + "," + address + "," + status + "," + manager + ")");
			
			statement.executeUpdate("insert into customer " +
					"values(" + "'" + customerIdentifier + "'" + "," + "'" + password + "'" + "," + "'" + name + "'" + "," + "'" + email + "'" + "," + "'" + address + "'" + "," + "'" + status + "'" + "," + "'" + manager + "'" + ")");
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
				System.out.println("in rs");
				System.out.println(rs.getString("cid") + " | " + rs.getString("password") + " | " + rs.getString("customer_name") + " | " + rs.getString("email") + " | " + rs.getString("address") + " | " + rs.getString("status") + " | " + rs.getString("is_manager"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
