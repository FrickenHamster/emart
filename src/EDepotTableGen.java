import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jessie on 3/8/14.
 */
public class EDepotTableGen
{
	private Connection connection;

	public EDepotTableGen(Connection connection)
	{
		this.connection = connection;
	}

	public void createTables()
	{
		{
			String createString = "create table DepotItem(" +
					"stock_number char(7)," +
					"quantity integer not null," +
					"max_stock integer not null," +
					"min_stock integer not null," +
					"replenish integer not null," +
					"location char(20) not null," +
					"mname char(20) not null," +
					"model_number char (20) not null," +
					"primary key (stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table DepotItem already exists");
				} else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table ShippingNotice(" +
					"ship_id integer," +
					"company_name char(20)," +
					"primary key (ship_id))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table ShippingNotice already exists");
				} else
					e.printStackTrace();

			}
		}

		{
			String createString = "create table ShippingListed(" +
					"ship_id integer," +
					"stock_number char(7)," +
					"amount integer not null," +
					"primary key (ship_id, stock_number)," +
					"foreign key (ship_id) references ShippingNotice (ship_id) on delete cascade," +
					"foreign key (stock_number) references DepotItem (stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table ShippingListed already exists");
				} else
					e.printStackTrace();

			}
		}

		{
			String createString = "create table ReplenishmentOrder(" +
					"order_id integer," +
					"mname char(20)," +
					"primary key (order_id))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table ReplenishmentOrder already exists");
				} else
					e.printStackTrace();
			}
		}
		{
			String createString = "create table InReplenishmentOrder(" +
					"order_id integer," +
					"stock_number char(7)," +
					"amount integer not null," +
					"primary key (order_id, stock_number)," +
					"foreign key (order_id) references ReplenishmentOrder (order_id) on delete cascade," +
					"foreign key (stock_number) references DepotItem (stock_number))";
			try
			{
				Statement statement = connection.createStatement();
				statement.execute(createString);
			} catch (SQLException e)
			{
				if (e.getMessage().startsWith("ORA-00955: name is already used by an existing object"))
				{
					System.out.println("table InReplenishmentOrder already exists");
				} else
					e.printStackTrace();
			}
		}
		
	}
	
	public void seedValues()
	{
		DepotItemModel depotItemModel = new DepotItemModel(connection);
		depotItemModel.insert("a",1,2,3,4,"b", "c", "d");
		depotItemModel.insert("AA00101", 2, 10, 1, 0, "A9", "HP", "6111");
		depotItemModel.insert("AA00201", 3, 15, 2, 0, "A7", "Dell", "420");
		depotItemModel.insert("AA00202", 4, 8, 2, 0, "B52", "Emachine", "3958");
		depotItemModel.insert("AA00301", 4, 6, 3, 0, "C27", "HP", "720");
		depotItemModel.insert("AA00302", 4, 6, 3, 0, "C13", "Samsung", "712");
		depotItemModel.insert("AA00401", 7, 9, 5, 0, "D27", "Symantec", "2005");
		depotItemModel.insert("AA00402", 7, 9, 5, 0, "D1", "Mcafee", "2005");
		depotItemModel.insert("AA00501", 3, 5, 2, 0, "E7", "HP", "1320");
		depotItemModel.insert("AA00601", 3, 9, 2, 0, "F9", "HP", "435");
		depotItemModel.insert("AA00602", 3, 5, 2, 0, "F3", "Cannon", "738");

        depotItemModel.insert("AA00902", 4,10,4,0, "Z1","Cannon", "718");
        depotItemModel.insert("AA00903", 3, 10,4,0, "Z2","Cannon", "728");
        depotItemModel.insert("AA00904", 3,10,4,0, "Z3","Cannon", "748");
        depotItemModel.insert("AA00905", 3, 10,4,0, "Z4","Cannon", "758");
		
		ReplenishmentOrderModel replenishmentOrderModel = new ReplenishmentOrderModel(connection);
		replenishmentOrderModel.setAll(100, "e");
		replenishmentOrderModel.insert();
		InReplenishmentOrder inReplenishmentOrder = new InReplenishmentOrder(connection);
		inReplenishmentOrder.insert(100, "a", 30);
		ShippingNoticeModel shippingNoticeModel = new ShippingNoticeModel(connection);
		shippingNoticeModel.insert(101, "comp");
		
		ShippingListedModel shippingListedModel = new ShippingListedModel(connection);
		shippingListedModel.insert("AA00602",101,3);
		WarehouseController warehouseController = new WarehouseController(connection);
	//	DepotItemModel.printAll();
        System.out.println("wtf");
        warehouseController.receiveShippingNotice(101);
		
		warehouseController.receiveShipment(101);
		warehouseController.fillCustomerOrder(0);
    //    DepotItemModel.printAll();
	}
	public void clearTables()
	{
		dropTable("InReplenishmentOrder");
		dropTable("ReplenishmentOrder");
		dropTable("ShippingListed");
		dropTable("ShippingNotice");
		dropTable("DepotItem");
	}
	private void dropTable(String tableName)
	{
		boolean ee = false;
		
		try
		{
			String sql = "drop table " + tableName;
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			if(e.getMessage().startsWith("ORA-00942: table or view does not exist"))
			{
				System.out.println(tableName + " does not exist");
			}
			else
				e.printStackTrace();
			ee = true;
		}
		if (!ee)
			System.out.println("table" + tableName + " dropped from database");
	}
}
