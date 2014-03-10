import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 3/3/14
 * Time: 1:45 PM
 */
public class DescriptionModel
{
	private Connection connection;
	private String stockNumber;
	private String attribute;
	private String value;

	public DescriptionModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String stockNumber, String attribute, String value)
	{
		this.stockNumber = stockNumber;
		this.attribute = attribute;
		this.value = value;
	}
	
	public void insert()
	{
		try
		{
			String insertString = "insert into Description values(?, ?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setString(1, stockNumber );
			insStmt.setString(2, attribute);
			insStmt.setString(3, value);

			insStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
