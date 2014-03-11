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
	
	
}
