import java.sql.*;

/**
 * Created by jessie on 3/11/14.
 */
public class WarehouseController
{
	private Connection connection;
	
	public WarehouseController(Connection connection)
	{
		this.connection = connection;
	}
	
	public void receiveShippingNotice(int shipid)
	{
		
		try
		{
			PreparedStatement statement = connection.prepareStatement("select stock_number, amount from ShippingListed where ship_id = ?");
			statement.setInt(1, shipid);
			
			ResultSet rs = statement.executeQuery();
			DepotItemModel depotItemModel = new DepotItemModel(connection);
			while (rs.next())
			{
				System.out.println("in shit");
				if(depotItemModel.load(rs.getString("stock_number")))
				{
					System.out.println("old replenish = " + depotItemModel.getReplenish());

					depotItemModel.setReplenish(depotItemModel.getReplenish() + rs.getInt("amount"));
					
					depotItemModel.update();
					System.out.println("amount = " + rs.getInt("amount"));
					System.out.println("new replenish = " + depotItemModel.getReplenish());
					
					
					
				}
				else{
					//TODO insert stock_num
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		

	}
	public void receiveShipment(int shipid)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select stock_number, amount from ShippingListed where ship_id = ?");
			stmt.setInt(1, shipid);
			ResultSet rs = stmt.executeQuery();
			DepotItemModel depotItemModel = new DepotItemModel(connection);
			while(rs.next())
			{
				
				if(depotItemModel.load(rs.getString("stock_number")))
				{
					System.out.println("old quantity: " + depotItemModel.getQuantity() );
						
						depotItemModel.setQuantity(depotItemModel.getQuantity() + depotItemModel.getReplenish());
					depotItemModel.setReplenish(depotItemModel.getReplenish() - rs.getInt("amount"));	
					depotItemModel.update();
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public int checkQuantity(String stockNumber)
	{
		int quantity = -1;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("select quantity from DepotItem where stockNumber = ?");
			stmt.setString(1, stockNumber);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				quantity = rs.getInt("quantity");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return quantity;
	}/*
	Fill an order which has a unique order number, and a list of items sold (and their quantities). The
	purchase needs to adjust the inventory. If there are three or more items from the same manufacturer in
	the inventory that go below their respective stock level, send a replenishment order to the manufacturer.
	The order should include all products from the manufacturer that are below their respective maximum
	stock levels.*/


	public void fillCustomerOrder(int order_id)
	{
		try
		{
			PreparedStatement stmt = connection.prepareStatement("");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
