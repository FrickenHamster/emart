
import oracle.jdbc.*;

import java.sql.*;

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
			/*MartItemModel.printAll();
			CustomerModel.printAll();*/
			
			CustomerController controller = new CustomerController(EMART_CONNECTION, "Rhagrid");
			CustomerView view = new CustomerView(controller);
			/*view.displayCart();
			view.searchStockNumber("AA00101");
			view.searchCategory("Desktop");*/
			controller.addToCart("AA00101", 2, 122.22);
			controller.addToCart("AA00401", 1, 1231.12);
			controller.deleteCartItem("AA00301");
			//view.displayCart();
			controller.fulfillOrder();
			controller.addToCart("AA00101", 3, 1111.1);
			controller.fulfillOrder();
			//SaleModel.printAll();
			ManagerController  mcon = new ManagerController(EMART_CONNECTION);
			ManagerView mview = new ManagerView(mcon);
			//mview.printSalesCategory("Laptop");
			/*CustomerModel.printAll();
			mcon.changeCustomerStatus("Rhagrid", "New");
			CustomerModel.printAll();*/
			SaleModel saleModel = new SaleModel(EMART_CONNECTION);
			OrderedItemModel orderModel = new OrderedItemModel(EMART_CONNECTION);
			saleModel.insert(5, "Mhooch", 13059.99, new Timestamp(2), 4, 4);
			saleModel.insert(7, "Mhooch", 13059.99, new Timestamp(1), 4, 4);
			saleModel.insert(8, "Mhooch", 13059.99, new Timestamp(3), 4, 4);
			saleModel.insert(6, "Mhooch", 13059.99, new Timestamp(4), 4, 4);
			saleModel.insert(6, "Mhooch", 13059.99, new Timestamp(4), 4, 4);
			saleModel.insert(6, "Mhooch", 13059.99, new Timestamp(4), 4, 4);
			//mcon.getMostCustomerSales();
			SaleModel.printAll();
			mcon.deleteNotNeededSale();
			System.out.println("__________");
			SaleModel.printAll();
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
