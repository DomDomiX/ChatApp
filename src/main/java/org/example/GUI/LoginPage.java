package org.example.GUI;

import org.example.DBConnection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage {
    private JFrame frame;
    private JTextField nameField;
    private JLabel nameLabel;
    private JPasswordField passwordField; // Použijeme JPasswordField pro heslo
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPage() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridBagLayout()); // Nastavení layoutu pro snadnější rozložení komponent
        frame.setLocationRelativeTo(null);

        // Inicializace komponent
        nameField = new JTextField(20);
        nameLabel = new JLabel("Username:");
        passwordField = new JPasswordField(20);
        passwordLabel = new JLabel("Password:");
        loginButton = new JButton("Login");
        registerButton = new JButton(("Register"));

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
        gbc.gridy = 2;
        frame.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(registerButton, gbc);

        // Zobrazení okna
        frame.setVisible(true);


        // Přidání ActionListener na tlačítko
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = logIn();
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose(); // Zavření přihlašovacího okna
                    new MainPage(); // Otevření hlavního okna
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new RegisterPage();
            }
        });
    }


    public boolean logIn() {
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