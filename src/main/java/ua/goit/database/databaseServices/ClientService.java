package ua.goit.database.databaseServices;

import ua.goit.database.Database;
import ua.goit.entity.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createStatement;
    private PreparedStatement selectMaxIdStatement;
    private PreparedStatement getByIDStatement;
    private PreparedStatement updateSetNameStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement selectAllStatement;

    public ClientService(Database database) throws SQLException {

        Connection connection = database.getConnection();

        createStatement = connection.prepareStatement("insert into client (name) VALUES(?)");
        selectMaxIdStatement = connection.prepareStatement("select max(id) as maxId from client");
        getByIDStatement = connection.prepareStatement("select name from client where id=?");
        updateSetNameStatement = connection.prepareStatement("update client set name=? where id=?");
        deleteByIdStatement = connection.prepareStatement("delete from client where id=?");
        selectAllStatement = connection.prepareStatement("select * from client");
    }

    // додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
    public long create(String name) {
        try {
            createStatement.setString(1, name);
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        long id;
        try (ResultSet rs = selectMaxIdStatement.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    //  повертає назву клієнта з ідентифікатором id
    public String getById(long id) {
        String name = "";
        try {
            getByIDStatement.setLong(1, id);
            ResultSet rs = getByIDStatement.executeQuery();
            rs.next();
            name = rs.getString("name");
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return name;
    }

    // встановлює нове ім'я name для клієнта з ідентифікатором id
    public void setName(long id, String name) {
        try {
            updateSetNameStatement.setString(1, name);
            updateSetNameStatement.setLong(2, id);
            updateSetNameStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // видаляє клієнта з ідентифікатором id
    public void deleteById(long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client (цей клас створи сам, у ньому має бути 2 поля - id та name)
    public List<Clients> listAll() {
        try (ResultSet rs = selectAllStatement.executeQuery()) {
            List<Clients> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                result.add(new Clients(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
