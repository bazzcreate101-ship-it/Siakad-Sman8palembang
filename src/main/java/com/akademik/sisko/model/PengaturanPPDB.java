package com.akademik.sisko.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pengaturan_ppdb")
public class PengaturanPPDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tanggal_buka", nullable = false)
    private LocalDate tanggalBuka;

    @Column(name = "tanggal_tutup", nullable = false)
    private LocalDate tanggalTutup;

    @Column(name = "tanggal_pengumuman", nullable = false)
    private LocalDate tanggalPengumuman;

    @Column(nullable = false)
    private Integer kuota;

    // Constructors
    public PengaturanPPDB() {
    }

    public PengaturanPPDB(LocalDate tanggalBuka, LocalDate tanggalTutup, LocalDate tanggalPengumuman, Integer kuota) {
        this.tanggalBuka = tanggalBuka;
        this.tanggalTutup = tanggalTutup;
        this.tanggalPengumuman = tanggalPengumuman;
        this.kuota = kuota;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTanggalBuka() {
        return tanggalBuka;
    }

    public void setTanggalBuka(LocalDate tanggalBuka) {
        this.tanggalBuka = tanggalBuka;
    }

    public LocalDate getTanggalTutup() {
        return tanggalTutup;
    }

    public void setTanggalTutup(LocalDate tanggalTutup) {
        this.tanggalTutup = tanggalTutup;
    }

    public LocalDate getTanggalPengumuman() {
        return tanggalPengumuman;
    }

    public void setTanggalPengumuman(LocalDate tanggalPengumuman) {
        this.tanggalPengumuman = tanggalPengumuman;
    }

    public Integer getKuota() {
        return kuota;
    }

    public void setKuota(Integer kuota) {
        this.kuota = kuota;
    }
}
