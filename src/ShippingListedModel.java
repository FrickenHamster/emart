/**
 * Created by jessie on 3/8/14.
 */
public class ShippingListedModel
{
	private String stockNumber;
	private int shipid;
	private int amount;
	
	public ShippingListedModel()
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

	public int getShipid()
	{
		return shipid;
	}

	public void setShipid(int shipid)
	{
		this.shipid = shipid;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
