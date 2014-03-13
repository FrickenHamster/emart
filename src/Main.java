
import oracle.jdbc.*;

import java.sql.*;
import java.util.*;

public class Main
{
	
	public static String hostAddress = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	public static String user = "aayan";
	public static String pass = "5608120";
	public static Connection EMART_CONNECTION;
	
	
	
	public static void main(String[] args) throws SQLException
	{
		EMartTableGenesis tableGen;
		EDepotTableGen depotGen;
		try
		{
			DriverManager.registerDriver(new OracleDriver());
			
			connectEMart();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (args.length > 0 && args[0].equals( "clear"))
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.clearTables();
			depotGen = new EDepotTableGen(EMART_CONNECTION);
			depotGen.clearTables();
			EMART_CONNECTION.close();
		
		}
		else if(args.length > 0 && args[0].equals( "test"))
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.clearTables();
			depotGen = new EDepotTableGen(EMART_CONNECTION);
			depotGen.clearTables();
			tableGen.createTables();
			depotGen.createTables();
			tableGen.seedValues();
            depotGen.seedValues();
			/*MartItemModel.printAll();
			CustomerModel.printAll();*/
			
			CustomerController controller = new CustomerController(EMART_CONNECTION, "Rhagrid");
			CustomerView view = new CustomerView(controller);
			/*view.displayCart();
			view.searchStockNumber("AA00101");
			view.searchCategory("Desktop");*/
			/*controller.addToCart("AA00101", 2, 122.22);
			controller.addToCart("AA00401", 1, 1231.12);
			controller.deleteCartItem("AA00301");
			view.displayCart();
			controller.fulfillOrder();
			controller.addToCart("AA00101", 3, 1111.1);
			controller.fulfillOrder();
			SaleModel.printAll();
			ManagerController  mcon = new ManagerController(EMART_CONNECTION);
			ManagerView mview = new ManagerView(mcon);
			//mview.printSalesCategory("Laptop");
			*//*CustomerModel.printAll();
			mcon.changeCustomerStatus("Rhagrid", "New");
			CustomerModel.printAll();*//*
			SaleModel saleModel = new SaleModel(EMART_CONNECTION);
			OrderedItemModel orderModel = new OrderedItemModel(EMART_CONNECTION);
			CustomerModel.printAll();
			saleModel.insert(5, "Mhooch", 1, new Timestamp(2), 4, 4);
			saleModel.insert(7, "Mhooch", 1, new Timestamp(1), 4, 4);
			saleModel.insert(8, "Mhooch", 1, new Timestamp(3), 4, 4);
			saleModel.insert(65, "Mhooch", 1, new Timestamp(4), 4, 4);
			saleModel.insert(62, "Mhooch", 100, new Timestamp(6), 4, 4);
			saleModel.insert(16, "Mhooch", 100, new Timestamp(8), 4, 4);
			//mcon.getMostCustomerSales();
			CustomerController cc = new CustomerController(EMART_CONNECTION, "Mhooch");
			cc.checkStatus();
			cc.fulfillOrder();
			CustomerModel.printAll();
			view.searchModelNumber("6111");*/
			/*
			SaleModel.printAll();
			mcon.deleteNotNeededSale();
			System.out.println("__________");
			SaleModel.printAll();*/
		}
		else if(args.length > 0 && args[0].equals( "manager"))
		{
			ManagerView mview = new ManagerView(new ManagerController(EMART_CONNECTION));
		}
		else if (args.length > 0 && args[0].equals("customer"))
		{
			System.out.println("Input Customer Identifier:");
			String cid = new Scanner(System.in).next();
			try
			{
				PreparedStatement stmt = EMART_CONNECTION.prepareStatement("select is_manager from customer where trim(cid) = trim(?)");
				stmt.setString(1, cid);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
				{
					if (rs.getString("is_manager").equals( "FALSE"))
					{
						CustomerView cview = new CustomerView(new CustomerController(EMART_CONNECTION, cid));
					}
					else
					{
						ManagerView mview = new ManagerView(new ManagerController(EMART_CONNECTION));
					}
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
        else if (args.length > 0 && args[0].equals( "warehouse"))
        {
            WarehouseView wview = new WarehouseView(new WarehouseController(EMART_CONNECTION));
        }
		else
		{
			tableGen = new EMartTableGenesis(EMART_CONNECTION);
			tableGen.createTables();
			depotGen = new EDepotTableGen(EMART_CONNECTION);
			depotGen.createTables();
			tableGen.seedValues();
			MartItemModel.printAll();
			CustomerModel.printAll();
		}

	}
	
	public static void connectEMart()
	{

		try
		{
			EMART_CONNECTION = DriverManager.getConnection(hostAddress, user, pass);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	public static void printAll() throws SQLException
	{
		
	}
	
}
