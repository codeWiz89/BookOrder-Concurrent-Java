
public class successOrder {
	
	String title = "";
	double price = 0.0; 
	double remainingBalance = 0.0;
	successOrder next;
	
	public successOrder(String title, double price, double remainingBalance, successOrder next) {
		
		this.title = title;
		this.price = price;
		this.remainingBalance = remainingBalance;
		this.next = next;
		
	}
	
	public String toString() {		
		
		return title + " " + price + " " + remainingBalance;
	}
}