package org.example;

import org.example.GUI.LoginPage;
import org.example.GUI.MainPage;
import com.formdev.flatlaf.FlatDarkLaf;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new LoginPage();

        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
    }
}