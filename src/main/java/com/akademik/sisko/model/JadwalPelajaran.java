package com.akademik.sisko.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "jadwal_pelajaran")
public class JadwalPelajaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kelas_id", nullable = false)
    private Kelas kelas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guru_id", nullable = false)
    private Guru guru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mata_pelajaran_id", nullable = false)
    private MataPelajaran mataPelajaran;

    @Column(nullable = false)
    private String hari; // Senin, Selasa, dst.

    @Column(name = "jam_mulai", nullable = false)
    private LocalTime jamMulai;

    @Column(name = "jam_selesai", nullable = false)
    private LocalTime jamSelesai;

    // Constructors
    public JadwalPelajaran() {
    }

    public JadwalPelajaran(Kelas kelas, Guru guru, MataPelajaran mataPelajaran, String hari, LocalTime jamMulai, LocalTime jamSelesai) {
        this.kelas = kelas;
        this.guru = guru;
        this.mataPelajaran = mataPelajaran;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public Guru getGuru() {
        return guru;
    }

    public void setGuru(Guru guru) {
        this.guru = guru;
    }

    public MataPelajaran getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(MataPelajaran mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }
}
