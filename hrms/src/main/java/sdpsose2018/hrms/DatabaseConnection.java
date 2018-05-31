package sdpsose2018.hrms;

import java.io.File;
import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
	private String user;
	private String password;
	private String connectionString;
	
	/**
	 * Constructor of DatabaseConnection
	 * @param host - String Host of the database.
	 * @param port - String Port of the database.
	 * @param dataBaseName - String Name of the database.
	 * @param user - String Name of the database user.
	 * @param password - String Password of the database user.
	 */
	private DatabaseConnection() {
		
		String host = "";
		String port = "";
		String dataBaseName = "";
		
		try {
			
			File xmlFile = new File(System.getProperty("user.dir") + "\\config.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			host = doc.getElementsByTagName("host").item(0).getTextContent();
			port = doc.getElementsByTagName("port").item(0).getTextContent();
			dataBaseName = doc.getElementsByTagName("dataBaseName").item(0).getTextContent();
			user = doc.getElementsByTagName("user").item(0).getTextContent();
			password = doc.getElementsByTagName("password").item(0).getTextContent();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("jdbc:mysql://");
		stringBuilder.append(host);
		stringBuilder.append(':');
		stringBuilder.append(port);
		stringBuilder.append('/');
		stringBuilder.append(dataBaseName);
		stringBuilder.append("?useSSL=True&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		
		this.connectionString = stringBuilder.toString();
		
	}
	
	/**
	 * Creates or returns the only Instance of DatabaseConnection.
	 * @return DatabaseConnection
	 */
	public static DatabaseConnection getInstance() {
		if (DatabaseConnection.instance == null) {	
			DatabaseConnection.instance = new DatabaseConnection();
		}
		return DatabaseConnection.instance;
	}
	
	/**
	 * This method is used for executing SQL strings.
	 * @param sql - String The SQL String
	 * @return ResultSet - Results of the SQL
	 */
	public ResultSet executeSQL(String sql) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connectionString, user, password);
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			connection.close();
			return results;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
