package com.divergentsl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.camunda.bpm.engine.identity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class CustomUser implements User, UserDetails {
    @Id
    @Column(name = "user_id")
    private String userId;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Username cannot be blank")
    @Column(name = "username")
    private String userName;

    @NotBlank(message = "First name cannot be blank")
    @Column(name = "firstname")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number format")
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = "User status cannot be blank")
    @Column(name = "user_status")
    private String userStatus;

    @NotBlank(message = "User type cannot be blank")
    @Column(name = "user_type")
    private String userType;

    @NotBlank(message = "Company cannot be blank")
    @Column(name = "company")
    private String company;

    @NotNull(message = "At date cannot be null")
    @Column(name = "at_date")
    private LocalDateTime atDate;

    @NotBlank(message = "Customer ID cannot be blank")
    @Column(name = "customer_id")
    private String customerId;

    @NotBlank(message = "Manager ID cannot be blank")
    @Column(name = "manager_id")
    private String managerId;

    @NotNull(message = "Last connection date cannot be null")
    @Column(name = "last_connection_date")
    private LocalDateTime lastConnectionDate;

    @NotNull(message = "Last login cannot be null")
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @NotNull(message = "Failed attempt cannot be null")
    @Column(name = "failed_attempt")
    private Integer failedAttempt;

    @NotNull(message = "Account non-locked status cannot be null")
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @NotBlank(message = "Encrypted password cannot be blank")
    @Column(name = "enc_pwd")
    private String encPwd;

    @NotNull(message = "Lock time cannot be null")
    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @NotBlank(message = "Account ID cannot be blank")
    @Column(name = "account_id")
    private String accountId;

    @NotBlank(message = "Roles can not be blank ")
    @Column(name = "roles")
    private String roles;

    @ManyToOne
    private CustomGroup customGroup;

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public void setId(String id) {
        this.userId = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

}