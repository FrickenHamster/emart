import oracle.sql.TIMESTAMP;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:22 PM
 */
public class SaleModel
{
	private Connection connection;
	private int saleNumber;
	private String customerIdentifier;
	private double total;
	private Timestamp date;


	public SaleModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(int saleNumber, String customerIdentifier, double total, Timestamp date)
	{
		this.saleNumber = saleNumber;
		this.customerIdentifier = customerIdentifier;
		this.total = total;
		this.date = date;
	}

	public void insert(int saleNumber, String customerIdentifier, double total, Timestamp date)
	{
		setAll(saleNumber, customerIdentifier, total, date);
		insert();
	}

	public void insert()
	{
		try
		{
			String insertString = "insert into sale values(?, ?, ?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setInt(1, saleNumber);
			insStmt.setString(2, customerIdentifier);
			insStmt.setDouble(3, total);
			insStmt.setTimestamp(4, date);
			insStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public int getSaleNumber()
	{
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber)
	{
		this.saleNumber = saleNumber;
	}

	public String getCustomerIdentifier()
	{
		return customerIdentifier;
	}

	public void setCustomerIdentifier(String customerIdentifier)
	{
		this.customerIdentifier = customerIdentifier;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public Timestamp getDate()
	{
		return date;
	}

	public void setDate(Timestamp date)
	{
		this.date = date;
	}


	public static void printAll()
	{
		try
		{

			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * from sale");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Orders:");
			while (rs.next())
			{
				System.out.println("Order:");
				System.out.println(rs.getString("order_id") + " | " + rs.getString("cid") + " | " + rs.getDouble("total") + " | " + rs.getTimestamp("order_date"));
				System.out.println("ordered items:");
				PreparedStatement desstmt = Main.EMART_CONNECTION.prepareStatement("select * from ordereditem where order_id = ?");
				desstmt.setString(1, rs.getString("order_id"));
				ResultSet desrs = desstmt.executeQuery();
				while (desrs.next())
				{
					System.out.println(desrs.getString("stock_number") + ": " + desrs.getInt("amount") + " $" + desrs.getDouble("item_total"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}