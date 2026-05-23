package com.akademik.sisko.model;

import jakarta.persistence.*;

@Entity
@Table(name = "siswa")
public class Siswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nisn", unique = true, nullable = false)
    private String nisn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calon_siswa_id", nullable = true)
    private CalonSiswa calonSiswa;

    @Column(name = "nama_lengkap", nullable = false)
    private String namaLengkap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kelas_id", nullable = true)
    private Kelas kelas;

    @Column(nullable = false)
    private String status; // AKTIF, LULUS, PINDAH, KELUAR

    // Constructors
    public Siswa() {
    }

    public Siswa(String nisn, CalonSiswa calonSiswa, String namaLengkap, Kelas kelas, String status) {
        this.nisn = nisn;
        this.calonSiswa = calonSiswa;
        this.namaLengkap = namaLengkap;
        this.kelas = kelas;
        this.status = status;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public CalonSiswa getCalonSiswa() {
        return calonSiswa;
    }

    public void setCalonSiswa(CalonSiswa calonSiswa) {
        this.calonSiswa = calonSiswa;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
