/**
 * Created by jessie on 3/6/14.
 */
public class AccessoryModel
{

	private int modelNumber;
	private String mName;

	public String getmName()
	{
		return mName;
	}

	public void setmName(String mName)
	{
		this.mName = mName;
	}

	public int getModelNumber()
	{
		return modelNumber;
	}

	public void setModelNumber(int modelNumber)
	{
		this.modelNumber = modelNumber;
	}

	public AccessoryModel(int modelNumber)
	{
		this.modelNumber = modelNumber;
	}
}
