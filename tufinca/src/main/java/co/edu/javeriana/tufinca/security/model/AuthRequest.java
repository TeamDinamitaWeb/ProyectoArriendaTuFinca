package co.edu.javeriana.tufinca.security.model;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

}
