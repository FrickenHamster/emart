import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 3/3/14
 * Time: 11:31 AM
  */
public class MartItemModel
{
	private Connection connection;
	
	private String stockNumber;
	private String category;
	private double price;
	private int warranty;
	private String manufacturer;
	private String modelNumber;

	public MartItemModel(Connection connection)
	{
		this.connection = connection;
	}

	public void setAll(String stockNumber, String category, double price, int warranty, String manufacturer, String modelNumber)
	{
		this.stockNumber = stockNumber;
		this.category = category;
		this.price = price;
		this.warranty = warranty;
		this.manufacturer = manufacturer;
		this.modelNumber = modelNumber;
	}
	
	public void insert(String stockNumber, String category, double price, int warranty, String manufacturer, String modelNumber)
	{
		setAll(stockNumber, category, price, warranty, manufacturer, modelNumber);
		insert();
	}
	
	public void insert()
	{
		try
		{
			String insertString = "insert into martitem values(?, ?, ?, ?, ?, ?)";
			PreparedStatement insStmt = connection.prepareStatement(insertString);
			insStmt.setString(1,  stockNumber );
			insStmt.setInt(2, warranty);
			insStmt.setDouble(3, price);
			insStmt.setString(4, category);
			insStmt.setString(5, manufacturer);
			insStmt.setString(6, modelNumber);
			insStmt.executeUpdate();//"insert into martitem values('sfe', 3, 43, 'afse', 'afewa', 'sdfe')");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void load(String stockNumber)
	{
		this.stockNumber = stockNumber;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select * from martitem where trim(stock_number) = trim(?)");
			stmt.setString(1, stockNumber);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				setAll(rs.getString("stock_number"), rs.getString("category"), rs.getDouble("price"), rs.getInt("warranty"), rs.getString("manufacturer"), rs.getString("model_number"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public double getPrice()
	{
		return price;
	}

	public static void printAll()
	{
		try
		{
			PreparedStatement stmt = Main.EMART_CONNECTION.prepareStatement("select * " +
					"from martitem");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Mart Items:");
			while (rs.next())
			{
				System.out.println("Item:");
				System.out.println(rs.getString("stock_number") + " | " + rs.getInt("warranty") + " | " + rs.getDouble("price") + " | " + rs.getString("category") + " | " + rs.getString("manufacturer") + " | " + rs.getString("model_number"));
				System.out.println("descriptions:");
				PreparedStatement desstmt = Main.EMART_CONNECTION.prepareStatement("select attribute, attribute_value from description where stock_number = ?");
				desstmt.setString(1, rs.getString("stock_number"));
				ResultSet desrs = desstmt.executeQuery();
				while(desrs.next())
				{
					System.out.println(desrs.getString("attribute") + ": " + desrs.getString("attribute_value"));
				}
				System.out.println("accessory to:");
				PreparedStatement accStmt = Main.EMART_CONNECTION.prepareStatement("select child_stock_number from accessory where parent_stock_number = ?");
				accStmt.setString(1, rs.getString("stock_number"));
				ResultSet accrs = accStmt.executeQuery();
				while(accrs.next())
				{
					System.out.println(accrs.getString("child_stock_number"));
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
