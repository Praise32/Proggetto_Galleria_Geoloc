package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {

	// ATTRIBUTI
	private static ConnessioneDB instance = null;
	public Connection connection = null;
	private String user = "postgres";
	private String password = "1234";
	private String url = "jdbc:postgresql://localhost:5432/NUN M RICORD O NOMME";
	private String driver = "org.postgresql.Driver";

	// COSTRUTTORE
	private ConnessioneDB() throws SQLException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			ex.printStackTrace();
		}

	}


	public static ConnessioneDB getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDB();
		} else if (instance.connection.isClosed()) {
			instance = new ConnessioneDB();
		}
		return instance;
	}
	
	
}
