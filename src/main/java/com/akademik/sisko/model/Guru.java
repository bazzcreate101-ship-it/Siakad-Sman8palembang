package com.akademik.sisko.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GURU")
public class Guru extends User {

    @Column(name = "nip", unique = true)
    private String nip;

    // Constructors
    public Guru() {
        super();
    }

    public Guru(String username, String password, String namaLengkap, String nip) {
        super(username, password, namaLengkap);
        this.nip = nip;
    }

    // Overriding methods for Polymorphism
    @Override
    public String getRole() {
        return "GURU";
    }

    @Override
    public String tampilkanDashboard() {
        return "/guru/dashboard";
    }

    // Getter and Setter (Manual implementation for Encapsulation)
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
