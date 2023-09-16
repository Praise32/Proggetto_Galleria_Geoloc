package MAIN;

public class User {
    private String username;
    private boolean admin;

    public User(){
        username = "";
        admin = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public boolean getAdmin() {
        return admin;
    }


}
