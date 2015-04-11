
public class bookOrderNode {
	
	String title = "";
	int id;
	double price;
	String category;
	
	public bookOrderNode(String title, int id, double price, String category) {
		
		this.title = title;
		this.id = id;
		this.price = price;
		this.category = category;
	}

	public String toString() {		
		
		return title + " " + id + " " + price + " " + category;
	}
}