package org.example.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
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

        // Model pro seznam přátel
        friendsModel = new DefaultListModel<>();
        friendsModel.addElement("Friend 1");
        friendsModel.addElement("Friend 2");
        friendsModel.addElement("Friend 3");
        friendsModel.addElement("Friend 4");

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

        // Zobrazení okna
        frame.setVisible(true);
    }
}
