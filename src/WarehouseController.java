import java.sql.*;
import java.util.Scanner;

/**
 * Created by jessie on 3/11/14.
 */
public class WarehouseController {
    private Connection connection;

    public WarehouseController(Connection connection) {
        this.connection = connection;
    }

    public void receiveShippingNotice(int shipid) {
        try {
			//System.out.println("WTF");
			PreparedStatement statement = connection.prepareStatement("select stock_number, amount from ShippingListed where ship_id = ?");
            statement.setInt(1, shipid);

            ResultSet rs = statement.executeQuery();
            DepotItemModel depotItemModel = new DepotItemModel(connection);
            System.out.println("You have a received a shipping notice");
            while (rs.next()) {
               // System.out.println("--in receiveShippingNotice");
                if (depotItemModel.load(rs.getString("stock_number"))) {
                    //System.out.println("old replenish = " + depotItemModel.getReplenish());

                    depotItemModel.setReplenish(depotItemModel.getReplenish() + rs.getInt("amount"));
                     System.out.println("Stock Number: " + rs.getString("stock_number") + "; Replenish Amount: " + rs.getInt("amount"));
                    depotItemModel.update();
                   /* System.out.println("amount = " + rs.getInt("amount"));
                    System.out.println("new replenish = " + depotItemModel.getReplenish());*/
                } else {
                    insertStockNumber(rs.getString("stock_number"), rs.getInt("amount"));
                }
            }
			System.out.println("Ship id is " + shipid);
		} catch (SQLException e) 
        {
            e.printStackTrace();
        }
       // System.out.println("--receiveShippingNotice END");
    }
    public void insertStockNumber(String stockNumber, int quantity)
    {
       DepotItemModel depotItemModel = new DepotItemModel(connection);
       
        System.out.println("Unrecognized StockNumber, please input fields");
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter maximum quantity: ");
        int max = scan.nextInt();
        System.out.println("Please enter minimum quantity: ");
        int min = scan.nextInt();
        System.out.println("Please enter future storage location: ");
        scan.nextLine();
		String location = scan.nextLine();
        System.out.println("Please enter manufacturer name: ");
        String mname = scan.nextLine();
        System.out.println("Please enter model number: ");
        String modelNumber = scan.nextLine();
       depotItemModel.insert(stockNumber, 0, max, min, quantity, location, mname, modelNumber);
        System.out.println("Would you like to print an all inventory currently in warehouse?" +
                " Please enter 0 for no, 1 for yes");
        int yn = scan.nextInt();
        if(yn == 1){
            depotItemModel.printAll();
        }
        
    }
    public void receiveShipment(int shipid) {
        try {
            PreparedStatement stmt = connection.prepareStatement("select stock_number, amount from ShippingListed where ship_id = ?");
            stmt.setInt(1, shipid);
            ResultSet rs = stmt.executeQuery();
            DepotItemModel depotItemModel = new DepotItemModel(connection);
            System.out.println("You have received a shipment.");
            
            Scanner scan = new Scanner(System.in);
            int input;
            while (rs.next()) {

                if (depotItemModel.load(rs.getString("stock_number"))) {
                  // System.out.println("old quantity: " + depotItemModel.getQuantity());
                  // 
                    int oldQuantity = depotItemModel.getQuantity();
                    System.out.println("Stock Number: " + rs.getString("stock_number") + "Previous quantity: " + 
                    oldQuantity + "Order Amount: " + rs.getInt("amount") + "New Quantity: " + depotItemModel.getQuantity());
                    
                    System.out.println("Would you like to accept shipment? Enter 1 for yes, 0 for no");
                    input = scan.nextInt();
                    if (input == 1)
                    {
                    depotItemModel.setQuantity(depotItemModel.getQuantity() + depotItemModel.getReplenish());
                    depotItemModel.setReplenish(depotItemModel.getReplenish() - rs.getInt("amount"));
                    
                    depotItemModel.update();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkQuantity(String stockNumber) {
        int quantity = -1;
        try {
            PreparedStatement stmt = connection.prepareStatement("select quantity from DepotItem where stock_number = ?");
            stmt.setString(1, stockNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }/*
    Fill an order which has a unique order number, and a list of items sold (and their quantities). The
	purchase needs to adjust the inventory. 
	
	If there are three or more items from the same manufacturer in
	the inventory that go below their respective stock level, send a replenishment order to the manufacturer.
	The order should include all products from the manufacturer that are below their respective maximum
	stock levels.*/

    //this orderid is from farfar away. not the replenishmentorder's order_id, but from some other foreign bitch
    public void fillCustomerOrder(int orderid) {
       //System.out.println("FillCustomerOrder-------------------");
        try {
            // Here you only need one statement I think. You are subtracting amount from stock number
            PreparedStatement stmt = connection.prepareStatement("select stock_number, amount from OrderedItem where order_id = ?");
            stmt.setInt(1, orderid);
            ResultSet rs = stmt.executeQuery();
            DepotItemModel depotItemModel = new DepotItemModel(connection);
            while (rs.next()) {
                /*System.out.println("you're the best");
                PreparedStatement sstmt = connection.prepareStatement("select amount from OrderedItem where stock_number = ?");
                sstmt.setString(1, rs.getString("stock_number"));
                ResultSet srs = sstmt.executeQuery();
                while(srs.next())
                {*/
                if (depotItemModel.load(rs.getString("stock_number"))) {

                    // depotItemModel.setStockNumber(rs.getString("stock_number"));

                  /*  System.out.println("sock number: " + depotItemModel.getStockNumber());
                    System.out.println("orderid: " + orderid + "; old quantity: " + depotItemModel.getQuantity()
                            + "; amount:" + rs.getInt("amount"));*/
                    depotItemModel.setQuantity(depotItemModel.getQuantity() - rs.getInt("amount"));
                    depotItemModel.update();
                  //  System.out.println("new quantity (should be same as current quantity): " + depotItemModel.getQuantity());
                    //depotItemModel.printAll();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkStockLevels();
      //  System.out.println("FillCustomerOrder END-------------------");
    }

    public void checkStockLevels() {
     //   System.out.println("checkstocklevels *****************");
        try {
            PreparedStatement stmt = connection.prepareStatement("select unique mname from DepotItem");

            ResultSet rs = stmt.executeQuery();

            //int number;
            while (rs.next()) {
                PreparedStatement sstmt = connection.prepareStatement("select count(stock_number) as numcount " +
                        "from DepotItem " +
                        "where quantity < min_stock and trim(mname) = trim(?)");
                sstmt.setString(1, rs.getString("mname"));
                ResultSet srs = sstmt.executeQuery();
                if (srs.next()) {
                    //System.out.println();
                //    System.out.println("mname: " + rs.getString("mname") + "; number: " + srs.getInt("numcount") + "; stock_number: ");
                    
                    if (srs.getInt("numcount") >= 3) 
                    {
                        
                        sendReplenishmentOrder(rs.getString("mname"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  System.out.println("checkStockLevels END*******************");
    }

    public void sendReplenishmentOrder(String mname) {
     //   System.out.println("SendReplenishmentOrder---------" + mname);
        try {
            PreparedStatement stmt = connection.prepareStatement("select stock_number, quantity, max_stock from DepotItem where trim(mname) = trim(?)");
            stmt.setString(1, mname);
            ResultSet rs = stmt.executeQuery();
            DepotItemModel depotItemModel = new DepotItemModel(connection);
            ShippingNoticeModel shippingNoticeModel = new ShippingNoticeModel(connection);
            ShippingListedModel shippingListedModel = new ShippingListedModel(connection);
            int id = getShipId();
            shippingNoticeModel.insert(id, "FEDEX");
            while (rs.next()) {

                //    if(depotItemModel.load(rs.getString("stock_number")))
                //   {
                int difference = rs.getInt("max_stock") - rs.getInt("quantity");
            //    System.out.println("difference: " + difference);

                if (difference > 0) {
            //        System.out.println("inserted: " + rs.getString("stock_number"));
                    shippingListedModel.insert(rs.getString("stock_number"), id, difference);
                    //warehouse sends request to manufacturer/manufacturer sends shipping notice
                }

                //depotItemModel.setQuantity(depotItemModel.getQuantity() - rs.getInt("amount"));
                //   }
            }
            receiveShippingNotice(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
     //   System.out.println("SendReplenishmentOrder END-------------");
    }

    public int getShipId() {
        try {
            PreparedStatement stmt = connection.prepareStatement("select max(ship_id) as maxid from ShippingNotice");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int maxid = rs.getInt("maxid") + 1;
            //    System.out.println("new shipid: " + maxid);
                return maxid;
            } else
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
