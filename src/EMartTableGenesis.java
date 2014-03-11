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
		discountModel.insert("New", 10);
		CustomerModel model = new CustomerModel(connection);
		
		model.insert("Rhagrid", "Rhagrid", "Rubeus Hagrid", "rhagrid@cs", "123 MyStreet, Goleta apt A, Ca", "Gold", "FALSE");
		model.insert("Mhooch", "Mhooch", "Madam Hooch", "mhooch@cs", "123 MyStreet, Goleta apt B, Ca", "Silver", "FALSE");
		model.insert("Amoody", "Amoody", "Alastor Moody", "amoody@cs", "123 MyStreet, Goleta apt C, Ca", "New", "FALSE");
		model.insert("Pquirrell", "Pquirrell", "Professor Quirrell", "pquirrell@cs", "123 MyStreet, Goleta apt D, Ca", "New", "FALSE");
		model.insert("Sblack", "Sblack", "Sirius Black", "sblack@cs", "123 MyStreet, Goleta apt E, Ca", "Green", "TRUE");
		model.insert("Ddiggle", "Ddiggle", "Dedalus Diggle", "ddiggle@cs", "123 MyStreet, Goleta apt F, Ca", "Green", "FALSE");
		
		itemModel.insert("AA00101", "Laptop", 1630, 12, "HP", "6111");
		descriptionModel.insert("AA00101", "Processor speed", "3.33Ghz");
		descriptionModel.insert("AA00101", "Ram size", "512 Mb");
		descriptionModel.insert("AA00101", "Hard disk size", "100Gb");
		descriptionModel.insert("AA00101", "Display Size", "17\"");
		itemModel.insert("AA00201", "Desktop", 239, 12, "Dell", "420");
		descriptionModel.insert("AA00201", "Processor speed", "2.53Ghz");
		descriptionModel.insert("AA00201", "Ram size", "256 Mb");
		descriptionModel.insert("AA00201", "Hard disk size", "80Gb");
		descriptionModel.insert("AA00201", "Os", "none");
		itemModel.insert("AA00202", "Desktop", 369.99, 12, "Emachine", "3958");
		descriptionModel.insert("AA00202", "Processor speed", "2.9Ghz");
		descriptionModel.insert("AA00202", "Ram size", "512 Mb");
		descriptionModel.insert("AA00202", "Hard disk size", "80Gb");
		
		itemModel.insert("AA00301", "Monitor", 69.99, 36, "Envision", "720");
		descriptionModel.insert("AA00301", "Size", "17\"");
		descriptionModel.insert("AA00301", "Weight", "25 lb");
		accessoryModel.insert("AA00301", "AA00201");
		accessoryModel.insert("AA00301", "AA00202");

		itemModel.insert("AA00302", "Monitor", 279.99, 36, "Samsung", "712");
		descriptionModel.insert("AA00302", "Size", "17\"");
		descriptionModel.insert("AA00302", "Weight", "9.6 lb");
		accessoryModel.insert("AA00302", "AA00201");
		accessoryModel.insert("AA00302", "AA00202");

		itemModel.insert("AA00401", "Software", 19.99, 60, "Symantec", "2005");
		descriptionModel.insert("AA00401", "Required disk size", "128 MB");
		accessoryModel.insert("AA00401", "AA00101");
		accessoryModel.insert("AA00401", "AA00201");
		accessoryModel.insert("AA00401", "AA00202");

		itemModel.insert("AA00402", "Software", 19.99, 60, "Mcafee", "2005");
		descriptionModel.insert("AA00402", "Required disk size", "128 MB");
		accessoryModel.insert("AA00402", "AA00101");
		accessoryModel.insert("AA00402", "AA00201");
		accessoryModel.insert("AA00402", "AA00202");
		
		itemModel.insert("AA00501", "Printer", 299.99, 12, "HP", "1320");
		descriptionModel.insert("AA00501", "Resolution", "1200 dpi");
		descriptionModel.insert("AA00501", "Sheet capacity", "500");
		descriptionModel.insert("AA00501", "Weight", ".4 lb");
		accessoryModel.insert("AA00501", "AA00201");
		accessoryModel.insert("AA00501", "AA00202");

		itemModel.insert("AA00601", "Camera", 119.99, 3, "HP", "435");
		descriptionModel.insert("AA00601", "Resolution", "3.1 Mp");
		descriptionModel.insert("AA00601", "Max zoom", "5 times");
		descriptionModel.insert("AA00601", "Weight", "24.7 lb");
		accessoryModel.insert("AA00601", "AA00201");
		accessoryModel.insert("AA00601", "AA00202");

		itemModel.insert("AA00602", "Camera", 329.99, 3, "Cannon", "738");
		descriptionModel.insert("AA00602", "Resolution", "3.1 Mp");
		descriptionModel.insert("AA00602", "Max zoom", "5 times");
		descriptionModel.insert("AA00602", "Weight", "24.7 lb");
		accessoryModel.insert("AA00602", "AA00201");
		accessoryModel.insert("AA00602", "AA00202");
		
		cartItemModel.insert("Rhagrid", "AA00101", 3);
		
		saleModel.insert(0, "Rhagrid", 200, new Timestamp(123123123));
		orderedItemModel.insert(0, "AA00301", 200, 2);
		
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
