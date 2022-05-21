package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
/**
 * Represents User Entity in SQL database
 * @param salt Randomly generated string to modify password-hash
 **/
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String salt;

    /**
     * returns byte array in ISO 8859 1 format as it preserves byte values
     *
     * @return Byte Array of salt
     */
    public byte[] getSaltBytes() {
        return getSalt().getBytes(StandardCharsets.ISO_8859_1);
    }

    public void setPassword(byte[] byteArray) {
        this.password = new String(byteArray, StandardCharsets.ISO_8859_1);
    }
}
