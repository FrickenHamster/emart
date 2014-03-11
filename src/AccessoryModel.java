import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/7/14
 * Time: 4:48 PM
 */
public class AccessoryModel
{
	private Connection connection;
	
	private String parentItem;
	private String accessoryItem;


	public AccessoryModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String parentItem, String accessoryItem)
	{
		this.parentItem = parentItem;
		this.accessoryItem = accessoryItem;
	}

	public void insert(String parentItem, String accessoryItem)
	{
		setAll(parentItem, accessoryItem);
		insert();
	}

	public void insert()
	{
		try
		{
			String insertString = "insert into accessory values(?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setString(1, parentItem);
			insStmt.setString(2, accessoryItem);
			insStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
