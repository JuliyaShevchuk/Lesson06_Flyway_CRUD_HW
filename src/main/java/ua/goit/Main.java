package ua.goit;

import ua.goit.database.Database;
import ua.goit.database.databaseServices.ClientService;
import ua.goit.entity.Clients;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Database database = Database.getInstance();
        ClientService clientService = new ClientService(database);
        long clientId = clientService.create("Ivanov");
        System.out.println("create Ivanov id=" + clientId);

        String clientName = clientService.getById(clientId);
        System.out.println("get by id=" + clientId + " name=" + clientName);

        clientService.setName(clientId, "Petrov");
        String clientNameUpdate = clientService.getById(clientId);
        System.out.println("update id=" + clientId + " was name=" + clientName + " update name=" + clientNameUpdate);

        clientService.deleteById(clientId);
        System.out.println("delete id=" + clientId);

        List<Clients> clients = clientService.listAll();
        if (clients.isEmpty()) {
            System.out.println("clients delete!");
        } else {
            System.out.println("All clients in table CLIENT:");
            System.out.println(clients);
        }


    }
}
