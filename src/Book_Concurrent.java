import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ArrayListMultimap;

public class Book_Concurrent {
	
	private int count = 0;
	private List<String> categories;
	private float revenue;

	private ConcurrentHashMap<Integer, personNode> personDB = new ConcurrentHashMap<Integer, personNode>(16, 0.75f, 3);
	private ArrayListMultimap<String, bookOrderNode> orderDB = ArrayListMultimap.create();

	public Book_Concurrent(String dbFileName, String ordersFileName, final List<String> categories) throws InterruptedException {

		this.categories = categories;
		
		readDBFile(dbFileName);
		printPersonDB();

		readOrderFile(ordersFileName);
		printOrderDB();
		
		getOrdersByCat();

		ArrayList<Thread> allThreads = new ArrayList<Thread>();

		for (int i = 0; i < categories.size(); i++) {
			
			Thread processThread = new Thread() {

				public void run() {

					processOrder(getCategory());

				}
			};

			allThreads.add(processThread);
		}
		
		for (Thread thread : allThreads) {

			thread.start();
		}
		
		boolean allDone = false;
		
		while (allDone == false) {
			
			boolean tempDone = false;
			
			for (Thread thread : allThreads) {

				if (thread.isAlive() == true) {
					
					tempDone = false;					
				}
				
				else {
					
					tempDone = true;
				}
			}
			
			if (tempDone == true) {
				
				allDone = true;
			}			
		}
		

		System.out.println();
		System.out.println();
		System.out.println("-------------------------------------------");
		
		printPersonDB();
		
		System.out.println("-------------------------------------------");
		System.out.println();
		System.out.println("Revenue: " + revenue);
		
		personDB.clear();
		orderDB.clear();
	}

	private String getCategory() {
			
		return categories.get(count++);
	}
	
/*	public void processOrder(String category) {
		
	/*	System.out.println();
		System.out.println();
		System.out.println("Category: " + category);
		System.out.println();
		System.out.println();
		
		*/

	/*	List<bookOrderNode> allOrders = orderDB.get(category);
		
//		System.out.println(allOrders);

		for (int i = 0; i < allOrders.size(); i++) {

			bookOrderNode bookOrder = allOrders.get(i);
			int id = bookOrder.id;			
			
		//	personNode person = personDB.get(id);

			synchronized (personDB) {
				
				personNode person = personDB.get(id);
								
				if (bookOrder.price <= person.balance) {

					double newBalance = person.balance - bookOrder.price;
					successOrder so = new successOrder(bookOrder.title, bookOrder.price, newBalance, null);
					person.balance = newBalance;
					revenue += bookOrder.price;
					
					if (person.so == null) {

						person.so = so;
					}

					else {

						successOrder prev = null;
						successOrder ptr = person.so;

						do {

							prev = ptr;
							ptr = ptr.next;

						} while (ptr != null);

						prev.next = so;
					}
				}

				else {

					failedOrder fo = new failedOrder(bookOrder.title, bookOrder.price, null);

					if (person.fo == null) {

						person.fo = fo;
					}

					else {

						failedOrder prev = null;
						failedOrder ptr = person.fo;

						do {

							prev = ptr;
							ptr = ptr.next;

						} while (ptr != null);

						prev.next = fo;
					}
				}				
			}
			
			printPersonDB();
		}		
	}

*/

	public void processOrder(String category) {
		
	}
	
	private ArrayList<bookOrderNode> getOrdersByCat() {
		
		Iterator itr = orderDB.keySet().iterator();
		List temp = null;
		
		while(itr.hasNext()) {
			
			temp = (List) itr.next();			
			System.out.println(temp);
		}
		
		return null;
	}
	
	public void readDBFile(String dbFileName) {

		BufferedReader br;

		try {

			br = new BufferedReader(new FileReader(dbFileName));

			String currentLine = "";
			int count = 0;

			String name = "";
			int id = 0;
			double balance = 0;
			String address = "";
			String state = "";
			String zip = "";

			while ((currentLine = br.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(currentLine, "|");

				while (st.hasMoreTokens()) {

					String temp = st.nextToken();

					if (count == 0) {

						name = temp.trim();
						count++;
					}

					else if (count == 1) {

						id = Integer.parseInt(temp.trim());
						count++;
					}

					else if (count == 2) {

						// temp = temp.substring(2, temp.length() - 1);

						balance = Double.parseDouble(temp.trim());
						count++;
					}

					else if (count == 3) {

						address = temp.trim();
						count++;
					}

					else if (count == 4) {

						state = temp.trim();
						count++;
					}

					else if (count == 5) {

						zip = temp.trim();
						count++;
					}
				}

				personNode person = new personNode(name, id, balance, address, state, zip, null, null);
				personDB.put(id, person);
				count = 0;
			}
		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void printPersonDB() {

		Iterator<?> personItr = personDB.keySet().iterator();

		while (personItr.hasNext()) {

			int key = (Integer) personItr.next();
			personNode person = personDB.get(key);
			
			System.out.println(person);
			
			if (person.so != null) {
				
				System.out.println("### Successful Orders ###");
				
				successOrder ptr = person.so;
				
				do {
					
					System.out.println(ptr);
					ptr = ptr.next;
					
				} while(ptr != null);
			}
			
			if (person.fo != null) {
				
				System.out.println("### Failed Orders ###");
				
				failedOrder ptr = person.fo;
				
				do {
					
					System.out.println(ptr);
					ptr = ptr.next;
					
				} while(ptr != null);
			}
			
			System.out.println();
			System.out.println();
		}
	}

	public void printOrderDB() {

		Iterator<?> personItr = orderDB.keySet().iterator();

		while (personItr.hasNext()) {

			String key = personItr.next().toString();

			List<bookOrderNode> allOrders = orderDB.get(key);

			for (bookOrderNode temp : allOrders) {

				System.out.println(temp);
			}
		}
	}

	public void readOrderFile(String orderFileName) {

		BufferedReader br;

		try {

			br = new BufferedReader(new FileReader(orderFileName));

			String currentLine = "";
			int count = 0;

			String title = "";
			int id = 0;
			double price = 0;
			String category = "";

			while ((currentLine = br.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(currentLine, "|");

				while (st.hasMoreTokens()) {

					String temp = st.nextToken();

					if (count == 0) {

						title = temp.trim();
						count++;
					}

					else if (count == 1) {

						// temp = temp.substring(0, temp.length() - 1);

						price = Double.parseDouble(temp.trim());
						count++;
					}

					else if (count == 2) {

						// temp = temp.substring(0, temp.length() - 1);

						id = Integer.parseInt(temp.trim());
						count++;

					}

					else if (count == 3) {

						category = temp.trim();
						count++;
					}
				}

				bookOrderNode bookOrder = new bookOrderNode(title, id, price, category);
				orderDB.put(category, bookOrder);
				count = 0;
			}
		}

		catch (IOException e) {

			e.printStackTrace();
		}

	}

}