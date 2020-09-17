package com.example.internship.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
