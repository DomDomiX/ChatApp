package org.example.GUI;

import org.example.Objects.Friend;
import org.example.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class friendsManagement {
    private User user;
    private JFrame frame;
    private JList<String> friendsList;
    private JButton addButton;
    private JButton removeButton;
    private DefaultListModel<String> listModel;

    public friendsManagement(DefaultListModel<String> sharedFriendsModel) {
        frame = new JFrame("Friends Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centrování okna

        this.user = User.getInstance();
        if (this.user == null) {
            JOptionPane.showMessageDialog(frame, "Chyba: uživatel není přihlášen!", "Chyba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userID = user.getId();
        String userName = user.getName();

        // Inicializace seznamu přátel
        listModel = new DefaultListModel<>();
        friendsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(friendsList);

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
                        try {
                            int id = Integer.parseInt(idText);
                            Friend newFriend = new Friend(id, name);
                            boolean success = newFriend.FriendRequest(id, userID);
                            if (success) {
                                listModel.addElement(newFriend.toString());
                                sharedFriendsModel.addElement(newFriend.toString());
                            } else {
                                JOptionPane.showMessageDialog(frame, "Přidání přítele selhalo!", "Chyba", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "ID musí být číslo!", "Chyba", JOptionPane.ERROR_MESSAGE);
                        }
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
}
