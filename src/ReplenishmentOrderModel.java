/**
 * Created by jessie on 3/8/14.
 */
public class ReplenishmentOrderModel
{
	private int shipID;
	private String companyName;
	
	public ReplenishmentOrderModel()
	{
		
	}
	
	public ReplenishmentOrderModel(int shipID)
	{
		
	}

	public int getShipID()
	{
		return shipID;
	}

	public void setShipID(int shipID)
	{
		this.shipID = shipID;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}
}
