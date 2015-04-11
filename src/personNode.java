
public class personNode {
	
	String name;
	int id;
	double balance;
	String address;
	String state;
	String zip;
	successOrder so;
	failedOrder fo;	
	
	public personNode(String name, int id, double balance, String address, String state, String zip, successOrder so, failedOrder fo) {
		
		this.name = name;
		this.id = id;
		this.balance = balance;
		this.address = address;
		this.state = state;
		this.zip = zip;	
		this.so = so;
		this.fo = fo;		
	}
	
	public String toString() {
		
		return name + " " + id + " " + balance + " " + address + " " + state + " " + zip;
	}
}