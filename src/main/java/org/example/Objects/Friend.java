package org.example.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friend {
    private int id;
    private String name;
    private String password;
    private int numFriends;

    public Friend(int id, String name, String password, int numFriends) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.numFriends = numFriends;
    }

    public boolean FriendRequest(int friendID, int userID) {
        boolean isAuthenticated = false;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "")) {
            System.out.println("Successfully connected to the database!");

            String query = "SELECT user_name FROM users.use_info WHERE ID = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, friendID);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                String query1 = "INSERT INTO friend_list (user_id, friend_id)\n\" + \"VALUES (?, ?);";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setInt(1, userID);
                statement1.setInt(2, friendID);

                int rowsAffected1 = statement1.executeUpdate();
                if (rowsAffected1 > 0) {
                    isAuthenticated = true;
                }
                else {
                    isAuthenticated = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;

    }
}
