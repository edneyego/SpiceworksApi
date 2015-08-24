package org.pcarrasco.spiceworksapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Entity model for User
 */
@Entity
@Table(name="users")
public class User implements ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="first_name")
    @Size(min = 0, max = 64)
    private String firstName;

    @Column(name="last_name")
    @Size(min = 0, max = 64)
    private String lastName;

    @Column(name="encrypted_password")
    @NotNull
    private String encryptedPassword;

    @Column(name="password_salt")
    private String passwordSalt;

    @NotNull
    @Size(min = 1, max = 80)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}
