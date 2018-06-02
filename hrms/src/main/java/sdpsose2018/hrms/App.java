package sdpsose2018.hrms;

/*
import java.sql.ResultSet;
import java.util.ArrayList;
*/

/**
 * Hello world!
 *
 */
public class App 
{
	static DatabaseConnection dbc = DatabaseConnection.getInstance();

	public static void main( String[] args )
	{
		System.out.println( "Hello World!" );
		
		/* SQL UPDATE EXAMPLE
		Boolean result = dbc.update("INSERT INTO countries (name, language, currency) VALUES ('Germany', 'german', 'EUR')");
		dbc.Close();
		System.out.println(result);
		*/
		
		/* SQL EXECUTE EXAMPLE + DISPLAY OF INFORMATION AND FILLING OBJECT
		ResultSet rs = dbc.execute("SELECT * FROM countries");
		ArrayList<Country> countries = new ArrayList<Country>();
		try {
			while(rs.next()) {
				Country country = new Country();
				country.id = rs.getInt("country_id");
				country.name = rs.getString("name");
				country.language = rs.getString("language");
				country.currency = rs.getString("currency");
				countries.add(country);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Results:");
		System.out.printf("%10s%10s%10s%10s%n","COUNTRY_ID","NAME","LANGUAGE","CURRENCY");
		for (Country country : countries) {
			System.out.printf("%10d%10s%10s%10s", country.id, country.name, country.language, country.currency);
		}
		*/
	}
}
