import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 3/10/14
 * Time: 9:27 PM
 */
public class ManagerView
{

	ManagerController controller;
	Scanner scanner;

	public void mainMenu()
	{
		System.out.println("Welcome grand overlord, What is your bidding?");
		System.out.println("1:Print monthly sales");
		System.out.println("2:Change Status");
		System.out.println("3:Make Replenishment Order");
		System.out.println("4:Delete Unneeded Sales");
		int input = scanner.nextInt();
		switch (input)
		{
			case 1:
				printSalesMenu();
			break;
			case 2:
				statusChangeMenu();;
				break;
			
			case 4:
				System.out.println("Precious Customer Information has been deleted");
				controller.deleteNotNeededSale();
				break;
		}
		mainMenu();
	}
	
	public void printSalesMenu()
	{
		System.out.println("Print by what?");
		System.out.println("1:sale by product");
		System.out.println("2:sale per category");
		System.out.println("3:customer most purchase");
		int input = scanner.nextInt();
		switch (input)
		{
			case 1:
				System.out.println("Input Stock Number");
				printSalesProduct(scanner.next());
				break;
			case 2:
				System.out.println("Input category");
				printSalesCategory(scanner.next());
				break;
			case 3:
				controller.getMostCustomerSales();
				break;
		}
		
	}
	
	public ManagerView(ManagerController controller)
	{
		this.controller = controller;
		scanner = new Scanner(System.in);
		
		mainMenu();
	}
	
	public void statusChangeMenu()
	{
		System.out.println("Which customer?");
		String cid = scanner.next();
		System.out.println("Change to which status");
		String status = scanner.next();
		controller.changeCustomerStatus(cid, status);
	}

	public void printSalesProduct(String stockNumber)
	{
		AmountPrice ap = controller.getSaleItem(stockNumber);
		System.out.println("Total sold for " + stockNumber + " is " + ap.amount + " total price: " + ap.price);
	}
	
	public void printSalesCategory(String category)
	{
		AmountPrice ap = controller.getSaleCategory(category);
		System.out.println("Total sold for " + category + " is " + ap.amount + " total price: " + ap.price);

	}
	
	
}
