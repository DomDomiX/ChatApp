package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class friendsManagement {
    private JFrame frame;
    private JList<String> friendsList;
    private JButton addButton;
    private JButton removeButton;
    private DefaultListModel<String> listModel;

    public friendsManagement() {
        frame = new JFrame("Friends Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centrování okna

        // Inicializace seznamu přátel
        listModel = new DefaultListModel<>();
        friendsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(friendsList);

        // Přidání demo dat (můžeš nahradit skutečnými daty z databáze)
        listModel.addElement("Friend 1");
        listModel.addElement("Friend 2");
        listModel.addElement("Friend 3");

        // Tlačítka pro přidání a odstranění přátel
        addButton = new JButton("Add Friend");
        removeButton = new JButton("Remove Friend");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Přidání komponent do okna
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Akce pro tlačítko přidání
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField idField = new JTextField();
                Object[] message = {
                        "Friend's Name:", nameField,
                        "Friend's ID:", idField
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "Add New Friend", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String idText = idField.getText().trim();
                    if (!name.isEmpty() && !idText.isEmpty()) {
                        int id = Integer.parseInt(idText);
                        String friend = name;
                        listModel.addElement(friend);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Name and ID cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Akce pro tlačítko odstranění
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = friendsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        // Zobrazení okna
        frame.setVisible(true);
    }

    public boolean addFriend(String name, int id) {
        boolean isAuthenticated = false;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "")) {
            System.out.println("Successfully connected to the database!");

            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String query = "SELECT * FROM use_info WHERE user_name = ? AND user_password = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isAuthenticated = true;
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
}
