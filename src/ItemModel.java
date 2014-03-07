/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 3/3/14
 * Time: 11:31 AM
  */
public class ItemModel
{
	
	private int stockNumber;
	private String category;
	private double price;
	private int warranty;
	
	public ItemModel()
	{
		
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}
