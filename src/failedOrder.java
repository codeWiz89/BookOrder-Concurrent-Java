
public class failedOrder {
	
	String title = "";
	double price = 0.0;
	failedOrder next;
	
	public failedOrder(String title, double price, failedOrder next) {
		
		this.title = title;
		this.price = price;
		this.next = next;
		
	}
	
	public String toString() {		
		
		return title + " " + price;
	}
}