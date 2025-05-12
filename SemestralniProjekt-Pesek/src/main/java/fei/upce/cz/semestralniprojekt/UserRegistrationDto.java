package fei.upce.cz.semestralniprojekt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "Uživatelské jméno je povinné")
    private String username;

    @NotBlank(message = "Heslo je povinné")
    @Size(min = 6, message = "Heslo musí mít alespoň 6 znaků")
    private String password;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}