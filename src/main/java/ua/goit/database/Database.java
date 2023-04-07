package ua.goit.database;

import ua.goit.prefs.Prefs;

import java.io.IOException;
import java.sql.*;


public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection = null;

    private Database() { }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try{
            String url = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
            connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public void close() throws IOException, SQLException {
        connection.close();
    }


}
