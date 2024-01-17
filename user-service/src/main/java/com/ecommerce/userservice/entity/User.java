package com.ecommerce.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 50)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank(message = "Phone Number is required")
    @Size(min = 10, max = 15)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses;

}
