package ua.goit.database.databaseServices;

import org.flywaydb.core.Flyway;
import ua.goit.prefs.Prefs;


public class DatabaseInitService {
    public static void main(String[] args)  {
        String url = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
        Flyway flyway = Flyway.configure().dataSource(url, "sa", null).load();
        flyway.migrate();
    }

}
