import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/8/14
 * Time: 9:18 PM
 */
public class EMartTableGenesis
{
	private Connection connection;
	public EMartTableGenesis(Connection connection)
	{
		this.connection = connection;
		
	}

	public void createTables()
	{
		// make sure you create the tables that are referenced by foreign constaints before you create the tables with foreign constraints in them.
		{
			String createString = "create table martitem(" +
					"stock_number char(7)," +
					"warranty integer not null," +
					"price real not null," +
					"category char(20)," +
					"manufacturer char(20)," +
					"model_number char(20)," +
					"primary key (stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table martitem already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Description(" +
					"stock_number char(7)," +
					"attribute char(20)," +
					"attribute_value char(20) not null," +
					"primary key (stock_number, attribute)," +
					"foreign key (stock_number) references martitem(stock_number) on delete cascade)";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table description already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Accessory(" +
					"parent_stock_number char(7)," +
					"child_stock_number char(7)," +
					"primary key (parent_stock_number, child_stock_number)," +
					"foreign key (parent_stock_number) references martitem(stock_number)," +
					"foreign key (child_stock_number) references martitem(stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table accessory already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Discount(" +
					"status char(20)," +
					"percent real not null," +
					"primary key(status))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table discount already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Customer(" +
					"cid char(20)," +
					"password char(20)," +
					"customer_name char(20)," +
					"email char(20)," +
					"address char(40)," +
					"status char(20)," +
					"is_manager char(5)," +
					"primary key (cid)," +
					"foreign key (status) references discount(status))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table customer already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table CartItem(" +
					"cid char(20)," +
					"stock_number char(7)," +
					"amount integer not null," +
					"primary key (cid, stock_number)," +
					"foreign key (cid) references customer(cid) on delete cascade," +
					"foreign key (stock_number) references martitem(stock_number) on delete cascade)";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table cartitem already exists");
				}
				else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table Sale(" +
					"order_number integer," +
					"cid char(20)," +
					"total real not null," +
					"order_date timestamp not null," +
					"primary key(order_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table sale already exists");
				}
				else
					e.printStackTrace();
			}	
		}
		{
			String createString = "create table OrderedItem(" +
					"order_number integer," +
					"stock_number char(7)," +
					"amount int not null," +
					"item_total real not null," +
					"primary key (order_number)," +
					"foreign key (order_number) references sale(order_number) on delete cascade," +
					"foreign key (stock_number) references martitem(stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table ordereditem already exists");
				}
				else
					e.printStackTrace();
			}
		}
	}
	
	public void seedValues()
	{
		DiscountModel discountModel = new DiscountModel(connection);
		AccessoryModel accessoryModel = new AccessoryModel(connection);
		CartItemModel cartItemModel = new CartItemModel(connection);
		SaleModel saleModel = new SaleModel(connection);
		MartItemModel itemModel = new MartItemModel(connection);
		OrderedItemModel orderedItemModel = new OrderedItemModel(connection);
		DescriptionModel descriptionModel = new DescriptionModel(connection);
		discountModel.insert("Gold", 10);
		discountModel.insert("Silver", 5);
		discountModel.insert("Green", 0);
		CustomerModel model = new CustomerModel(connection);
		model.insert("Rhagrid", "Rhagrid", "Rubeus Hagrid", "rhagrid@cs", "123 MyStreet, Goleta apt A, Ca", "Gold", "FALSE");
		itemModel.insert("AA00101", "Laptop", 1630, 12, "HP", "6111");
		descriptionModel.setAll("AA00101", "Processor speed", "3.33Ghz");
		descriptionModel.insert();
		descriptionModel.setAll("AA00101", "Ram size", "512 Mb");
		descriptionModel.insert();
		descriptionModel.setAll("AA00101", "Hard disk size", "100Gb");
		descriptionModel.insert();
		descriptionModel.setAll("AA00101", "Display Size", "17\"");
		descriptionModel.insert();
		itemModel.setAll("AA00201", "Desktop", 239, 12, "Dell", "420");
		itemModel.insert();
		descriptionModel.setAll("AA00201", "Processor speed", "2.53Ghz");
		descriptionModel.insert();
		descriptionModel.setAll("AA00201", "Ram size", "256 Mb");
		descriptionModel.insert();
		descriptionModel.setAll("AA00201", "Hard disk size", "80Gb");
		descriptionModel.insert();
		descriptionModel.setAll("AA00201", "Os", "none");
		descriptionModel.insert();
		itemModel.setAll("AA00202", "Desktop", 369.99, 12, "Emachine", "3958");
		itemModel.insert();
		descriptionModel.setAll("AA00202", "Processor speed", "2.9Ghz");
		descriptionModel.insert();
		descriptionModel.setAll("AA00202", "Ram size", "512 Mb");
		descriptionModel.insert();
		descriptionModel.setAll("AA00202", "Hard disk size", "80Gb");
		descriptionModel.insert();
		
		itemModel.setAll("AA00301", "Monitor", 69.99, 36, "Envision", "720");
		itemModel.insert();
		descriptionModel.setAll("AA00301", "Size", "17\"");
		descriptionModel.insert();
		descriptionModel.setAll("AA00301", "Weight", "25 lb");
		descriptionModel.insert();
		accessoryModel.setAll("AA00301", "AA00201");
		accessoryModel.insert();
		accessoryModel.setAll("AA00301", "AA00202");
		accessoryModel.insert();

		itemModel.setAll("AA00302", "Monitor", 279.99, 36, "Samsung", "712");
		itemModel.insert();
		descriptionModel.setAll("AA00302", "Size", "17\"");
		descriptionModel.insert();
		descriptionModel.setAll("AA00302", "Weight", "9.6 lb");
		descriptionModel.insert();
		accessoryModel.setAll("AA00302", "AA00201");
		accessoryModel.insert();
		accessoryModel.setAll("AA00302", "AA00202");
		accessoryModel.insert();

		itemModel.setAll("AA00401", "Software", 19.99, 60, "Symantec", "2005");
		itemModel.insert();
		
		cartItemModel.setAll("Rhagrid", "AA00101", 3);
		cartItemModel.insert();
		
		saleModel.setAll(0, "Rhagrid", 200, new Timestamp(123123123));
		saleModel.insert();
		orderedItemModel.setAll(0, "AA00301", 200, 2);
		orderedItemModel.insert();
		
	}
	
	public void clearTables()
	{
		// When clearing tables, make sure you remove the ones with the foreign constraints first
		dropTable("accessory");
		dropTable("description");
		dropTable("cartitem");
		dropTable("ordereditem");
		dropTable("customer");
		dropTable("martitem");
		
		
		dropTable("discount");
		dropTable("sale");
		
	}
	private void dropTable(String tableName)
	{
		boolean ee = false;
		try
		{
			String sql = "drop table " + tableName;
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e)
		{
			if (e.getMessage().startsWith("ORA-00942: table or view does not exist"))
				System.out.println(tableName + " does not exist");
			else
				e.printStackTrace();
			ee = true;
		}
		if (!ee)
			System.out.println("table " + tableName + " dropped from database");

	}
	
	
}
