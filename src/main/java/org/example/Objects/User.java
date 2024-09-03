package org.example.Objects;

public class User {
    private int id;
    private String name;
    private String password;
    private int numFriends;

    private static User instance;

    // Soukromý konstruktor, aby bylo zabráněno přímému vytváření instance
    private User(int id, String name, String password, int numFriends) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.numFriends = numFriends;
    }

    // Metoda pro získání instance (vytvoření pouze při prvním volání)
    public static User getInstance(int id, String name, String password, int numFriends) {
        if (instance == null) {
            instance = new User(id, name, password, numFriends);
        }
        return instance;
    }

    public static User getInstance() {
        return instance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumFriends() {
        return numFriends;
    }
}
