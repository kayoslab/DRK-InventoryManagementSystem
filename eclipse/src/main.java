import model.databaseCommunication.DatabaseLoginManager;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseLoginManager dblogin = new DatabaseLoginManager("Hallo","Welt","https://baum.wald/");
		System.out.println(dblogin.getUsername());
		System.out.println(dblogin.getPassword());
		System.out.println(dblogin.getURL());
	}

}
