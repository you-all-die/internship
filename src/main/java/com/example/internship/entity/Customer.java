package com.example.internship.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.StringJoiner;

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

    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name="last_activity")
    private Timestamp lastActivity;

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
