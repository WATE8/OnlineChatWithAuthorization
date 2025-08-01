package chat;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;

    private String password;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
