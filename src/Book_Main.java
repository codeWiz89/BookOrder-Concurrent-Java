import java.util.*;

public class Book_Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		List<String> categories = new ArrayList<String>();
		categories.add("HOUSING01");
		categories.add("POLITICS01");
		categories.add("SPORTS01");
		
//		Book_Linked bookL = new Book_Linked("database.txt", "orders.txt", categories);
		
		Book_Concurrent bookC = new Book_Concurrent("database.txt", "orders.txt", categories);

	}

}
