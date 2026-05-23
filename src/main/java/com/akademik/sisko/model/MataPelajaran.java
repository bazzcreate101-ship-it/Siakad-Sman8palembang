package com.akademik.sisko.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mata_pelajaran")
public class MataPelajaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_mapel", unique = true, nullable = false)
    private String kodeMapel;

    @Column(name = "nama_mapel", nullable = false)
    private String namaMapel;

    // Constructors
    public MataPelajaran() {
    }

    public MataPelajaran(String kodeMapel, String namaMapel) {
        this.kodeMapel = kodeMapel;
        this.namaMapel = namaMapel;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeMapel() {
        return kodeMapel;
    }

    public void setKodeMapel(String kodeMapel) {
        this.kodeMapel = kodeMapel;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }
}
