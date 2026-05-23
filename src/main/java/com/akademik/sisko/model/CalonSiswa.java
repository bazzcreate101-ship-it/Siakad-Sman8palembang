package com.akademik.sisko.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calon_siswa")
public class CalonSiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", unique = true, nullable = false)
    private String nik;

    @Column(name = "nama_lengkap", nullable = false)
    private String namaLengkap;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(name = "jenis_kelamin", nullable = false)
    private String jenisKelamin;

    @Column(nullable = false)
    private String alamat;

    @Column(name = "nama_orang_tua", nullable = false)
    private String namaOrangTua;

    @Column(nullable = false)
    private String telepon;

    @Column(name = "status_pendaftaran", nullable = false)
    private String statusPendaftaran; // MENUNGGU_VERIFIKASI, DITERIMA, DITOLAK

    // Constructors
    public CalonSiswa() {
    }

    public CalonSiswa(String nik, String namaLengkap, LocalDate tanggalLahir, String jenisKelamin, String alamat, String namaOrangTua, String telepon, String statusPendaftaran) {
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.namaOrangTua = namaOrangTua;
        this.telepon = telepon;
        this.statusPendaftaran = statusPendaftaran;
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaOrangTua() {
        return namaOrangTua;
    }

    public void setNamaOrangTua(String namaOrangTua) {
        this.namaOrangTua = namaOrangTua;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getStatusPendaftaran() {
        return statusPendaftaran;
    }

    public void setStatusPendaftaran(String statusPendaftaran) {
        this.statusPendaftaran = statusPendaftaran;
    }
}
