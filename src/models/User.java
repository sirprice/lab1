package models;

/**
 * Created by cj on 17/12/15.
 */
public class User {

    private String userID;
    private final String username;

    public User (String userID, String username){
        this.userID = userID;
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                '}';
    }
}
