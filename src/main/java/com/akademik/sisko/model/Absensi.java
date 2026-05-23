package com.akademik.sisko.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absensi")
public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siswa_id", nullable = false)
    private Siswa siswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kelas_id", nullable = false)
    private Kelas kelas;

    @Column(nullable = false)
    private LocalDate tanggal;

    @Column(nullable = false)
    private String status; // HADIR, SAKIT, IZIN, ALPA

    // Constructors
    public Absensi() {
    }

    public Absensi(Siswa siswa, Kelas kelas, LocalDate tanggal, String status) {
        this.siswa = siswa;
        this.kelas = kelas;
        this.tanggal = tanggal;
        this.status = status;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Siswa getSiswa() {
        return siswa;
    }

    public void setSiswa(Siswa siswa) {
        this.siswa = siswa;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
