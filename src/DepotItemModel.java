/**
 * Created by jessie on 3/8/14.
 */
public class DepotItemModel
{
	private String stockNumber;
	private int quantity;
	private int maxStock;
	private int minStock;
	private int replenish;
	private String location;
	private String mName;
	private String modelNumber;
	
	public DepotItemModel()
	{
		
	}
	public DepotItemModel(String stockNumber)
	{
		
	}

	public String getStockNumber()
	{
		return stockNumber;
	}

	public void setStockNumber(String stockNumber)
	{
		this.stockNumber = stockNumber;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public int getMaxStock()
	{
		return maxStock;
	}

	public void setMaxStock(int maxStock)
	{
		this.maxStock = maxStock;
	}

	public int getMinStock()
	{
		return minStock;
	}

	public void setMinStock(int minStock)
	{
		this.minStock = minStock;
	}

	public int getReplenish()
	{
		return replenish;
	}

	public void setReplenish(int replenish)
	{
		this.replenish = replenish;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getmName()
	{
		return mName;
	}

	public void setmName(String mName)
	{
		this.mName = mName;
	}

	public String getmodelNumber()
	{
		return modelNumber;
	}

	public void setmodelNumber(String modelNumber)
	{
		this.modelNumber = modelNumber;
	}
}
