package com.akademik.sisko.model;

import jakarta.persistence.*;

@Entity
@Table(name = "kelas")
public class Kelas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_kelas", unique = true, nullable = false)
    private String namaKelas;

    @Column(nullable = false)
    private Integer tingkat; // e.g., 10, 11, 12

    // Constructors
    public Kelas() {
    }

    public Kelas(String namaKelas, Integer tingkat) {
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public Integer getTingkat() {
        return tingkat;
    }

    public void setTingkat(Integer tingkat) {
        this.tingkat = tingkat;
    }
}
