package MAIN;

public class User {
    private static User istance = null;
    private String username = "";

    public static User getInstance() {
        if (istance == null) {
            istance = new User();
        }
        return istance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}