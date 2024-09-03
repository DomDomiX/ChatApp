package org.example.Objects;

import java.sql.*;

public class Friend {
    private int id;
    private String name;
    private String password;
    private int numFriends;

    public Friend(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean FriendRequest(int friendID, int userID) {
        boolean isAuthenticated = false;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "")) {
            System.out.println("Successfully connected to the database!");

            String query = "SELECT user_name FROM use_info WHERE ID = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, friendID);

            ResultSet rowsAffected = statement.executeQuery();

            if (rowsAffected.next()) {
                String query1 = "INSERT INTO friend_list (user_id, friend_id) VALUES (?, ?)";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setInt(1, userID);
                statement1.setInt(2, friendID);

                String query2 = "INSERT INTO friend_list (user_id, friend_id) VALUES (?, ?)";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setInt(1, friendID);
                statement2.setInt(2, userID);

                int rowsAffected1 = statement1.executeUpdate();
                int rowsAffected2 = statement2.executeUpdate();
                if (rowsAffected1 > 0 && rowsAffected2 > 0) {
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

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
