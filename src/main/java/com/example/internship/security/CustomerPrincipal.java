package com.example.internship.security;

import com.example.internship.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerPrincipal implements UserDetails {

    private Customer customer;

    public CustomerPrincipal(Customer customer) {
        this.customer = customer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

//        // Extract list of permissions (name)
//        this.customer.getPermissionList().forEach(p -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority(p);
//            authorities.add(authority);
//        });
//
//        // Extract list of roles (ROLE_name)
//        this.customer.getRoleList().forEach(r -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
//            authorities.add(authority);
//        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getEmail();
    }

    public String getUserId() {
        return String.valueOf(this.customer.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
