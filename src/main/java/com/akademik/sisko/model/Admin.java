package com.akademik.sisko.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    // Constructors
    public Admin() {
        super();
    }

    public Admin(String username, String password, String namaLengkap) {
        super(username, password, namaLengkap);
    }

    // Overriding methods for Polymorphism
    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public String tampilkanDashboard() {
        return "/admin/dashboard";
    }
}
