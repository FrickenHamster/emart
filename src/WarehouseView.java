import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by jessie on 3/13/14.
 */
public class WarehouseView 
{
    private Connection connection;
    private WarehouseController warehouseController;
    private Scanner scanner;
    
    public WarehouseView(WarehouseController warehouseController)
    {
        this.warehouseController = warehouseController;
        System.out.println("Welcome to warehouse");
        scanner = new Scanner(System.in);
        mainMenu();
    }
    
    public void mainMenu()
    {
        System.out.println("What has happened in the real world?");
        System.out.println("1: Print Warehouse Inventory");
        System.out.println("2: Check Item Quantity");
        System.out.println("3: Recieve Shipment");
        int input = scanner.nextInt();
        switch (input)
        {
            /*case 1:
                mreceiveShippingNotice();
                break;*/
            case 1:
                warehouseInventory();
                break;
            case 2:
                mcheckQuantity();
                break;
			case 3:
				System.out.println("Which shipid to accept?");
				warehouseController.receiveShipment(scanner.nextInt());
				break;
				
			default:
                System.out.println("Sorry invalid selection, please choose again.");
                mainMenu();
                break;
        }
		mainMenu();
    }
    public void warehouseInventory()
    {
        DepotItemModel depotItemModel = new DepotItemModel(connection);
        depotItemModel.printAll();
    //receiveShippingNotice(input);
    }
    public void mcheckQuantity()
    {
        System.out.println("Which Stock number to check master?");
        scanner.nextLine();
        String input = scanner.nextLine();
        System.out.println("For " + input + "quantity is: " + warehouseController.checkQuantity(input));
    }
}
