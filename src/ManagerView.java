/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/10/14
 * Time: 9:27 PM
 */
public class ManagerView
{

	ManagerController controller;

	public ManagerView(ManagerController controller)
	{
		this.controller = controller;
	}

	public void printSalesProduct(String stockNumber)
	{
		AmountPrice ap = controller.getSaleItem(stockNumber);
		System.out.println("Total sold for " + stockNumber + " is " + ap.amount + " total price: " + ap.price);
	}
	
	public void printSalesCategory(String category)
	{
		
	}
	
	
}
