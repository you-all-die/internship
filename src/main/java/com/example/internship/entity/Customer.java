package com.example.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String phone;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "customerId")
    private Set<Address> addresses;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    public final String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (null != lastName) {
            sb.append(lastName);
        }
        if (null != firstName) {
            sb.append(" ").append(firstName);
        }
        if (null != middleName) {
            sb.append(" ").append(middleName);
        }
        return sb.toString().trim();
    }
}
