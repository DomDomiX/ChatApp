package org.example.GUI;
import org.example.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainPage {
    private User user;
    private JFrame frame;
    private JList<String> friendsList;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private DefaultListModel<String> friendsModel;

    public MainPage() {
        // Vytvoření hlavního okna
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 720);
        frame.setLocationRelativeTo(null);

        //Vytvoření Menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menuSettigns = new JMenu("Settings");
        JMenuItem changeTheme = new JMenuItem("Change theme");

        JMenu menuFriends = new JMenu("Friends");
        JMenuItem addFriend = new JMenuItem("Manage friends");

        menuBar.add(menuSettigns);
        menuBar.add(menuFriends);

        menuSettigns.add(changeTheme);
        menuFriends.add(addFriend);

        addFriend.addActionListener(e -> new friendsManagement(friendsModel));

        frame.setJMenuBar(menuBar);

        // Model pro seznam přátel
        friendsModel = new DefaultListModel<>();

        this.user = User.getInstance();
        if (this.user == null) {
            JOptionPane.showMessageDialog(frame, "Chyba: uživatel není přihlášen!", "Chyba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userID = user.getId();
        String userName = user.getName();

        // Vytvoreni panelu s uzivatelovi informacemi
        JPanel userInfo = new JPanel();
        userInfo.setLayout(new GridLayout(2, 1)); // Layout pro dávání Labelu vertikalne
        JLabel userLabel = new JLabel("User: " + userName);
        JLabel userIDLabel = new JLabel("User ID: " + userID);
        userInfo.add(userLabel);
        userInfo.add(userIDLabel);

        // Pridani informaci do framu
        frame.add(userInfo, BorderLayout.NORTH); // Pridani panelu s informacemi nahoru

        // Seznam přátel
        friendsList = new JList<>(friendsModel);
        friendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friendsList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane friendsScrollPane = new JScrollPane(friendsList);

        // Chatovací oblast
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        // Pole pro zadávání zpráv
        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        // Rozdělení hlavního okna na dvě části
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, friendsScrollPane, chatPanel);
        splitPane.setDividerLocation(150);
        frame.add(splitPane);

        // Akce při stisknutí tlačítka nebo Enter
        /*sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });*/

        loadFriendsList();

        // Zobrazení okna
        frame.setVisible(true);
    }

    private void loadFriendsList() {
        // Clear the list model before adding new items
        friendsModel.clear();

        // Get the user ID
        int userID = user.getId();

        // Database connection and query
        String url = "jdbc:mysql://localhost:3306/users";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT u.user_name, u.ID\n" +
                "FROM users.friend_list f\n" +
                "INNER JOIN users.use_info u ON f.friend_id = u.ID\n" +
                "WHERE f.user_id = ?;";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String friendName = resultSet.getString("user_name");
                int id = resultSet.getInt("ID");
                friendsModel.addElement(friendName + " (ID: " + id + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load friends list from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
