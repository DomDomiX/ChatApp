package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterPage {
    private JFrame frame;
    private JTextField nameField;
    private JLabel nameLabel;
    private JPasswordField passwordField; // Použijeme JPasswordField pro heslo
    private JLabel passwordLabel;
    private JButton registerButton;

    public RegisterPage() {
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridBagLayout()); // Nastavení layoutu pro snadnější rozložení komponent
        frame.setLocationRelativeTo(null);

        // Inicializace komponent
        nameField = new JTextField(20);
        nameLabel = new JLabel("Username:");
        passwordField = new JPasswordField(20);
        passwordLabel = new JLabel("Password:");
        registerButton = new JButton("Register");

        // Nastavení layoutu a přidání komponent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Okraje mezi komponentami

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(registerButton, gbc);

        // Zobrazení okna
        frame.setVisible(true);


        // Přidání ActionListener na tlačítko
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = registerIn();
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Register successful!");
                    frame.dispose(); // Zavření přihlašovacího okna
                }
            }
        });
    }

    public boolean registerIn() {
        boolean isAuthenticated = false;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "")) {
            System.out.println("Successfully connected to the database!");

            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String query = "INSERT INTO use_info (user_name, user_password, number_friends)\n" + "VALUES (?, ?, 0);";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, password);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                isAuthenticated = true;
                System.out.println("Register successful!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
}
