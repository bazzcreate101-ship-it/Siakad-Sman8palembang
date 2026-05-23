package com.akademik.sisko.model;

import java.util.List;

public class Rapor {
    private Siswa siswa;
    private List<Nilai> listNilai;
    private int jumlahHadir;
    private int jumlahSakit;
    private int jumlahIzin;
    private int jumlahAlpa;
    private String catatanWaliKelas;

    // Constructors
    public Rapor() {
    }

    public Rapor(Siswa siswa, List<Nilai> listNilai, int jumlahHadir, int jumlahSakit, int jumlahIzin, int jumlahAlpa) {
        this.siswa = siswa;
        this.listNilai = listNilai;
        this.jumlahHadir = jumlahHadir;
        this.jumlahSakit = jumlahSakit;
        this.jumlahIzin = jumlahIzin;
        this.jumlahAlpa = jumlahAlpa;
        this.catatanWaliKelas = tentukanCatatanWaliKelas();
    }

    // Logic to automatically determine notes based on average grades
    private String tentukanCatatanWaliKelas() {
        if (listNilai == null || listNilai.isEmpty()) {
            return "Belum ada data nilai yang diinput oleh guru.";
        }
        double total = 0;
        for (Nilai n : listNilai) {
            total += n.getNilaiAkhir();
        }
        double rataRata = total / listNilai.size();
        
        if (rataRata >= 85) {
            return "Prestasi luar biasa! Pertahankan konsistensi belajar dan teruslah berprestasi.";
        } else if (rataRata >= 75) {
            return "Hasil belajar cukup memuaskan. Tingkatkan intensitas belajar di semester berikutnya.";
        } else {
            return "Perlu perhatian khusus. Belajarlah lebih giat dan konsultasikan kesulitan belajar dengan guru pengampu.";
        }
    }

    // Getters and Setters (Manual implementation for Encapsulation)
    public Siswa getSiswa() {
        return siswa;
    }

    public void setSiswa(Siswa siswa) {
        this.siswa = siswa;
    }

    public List<Nilai> getListNilai() {
        return listNilai;
    }

    public void setListNilai(List<Nilai> listNilai) {
        this.listNilai = listNilai;
        this.catatanWaliKelas = tentukanCatatanWaliKelas(); // Re-calculate when grade list changes
    }

    public int getJumlahHadir() {
        return jumlahHadir;
    }

    public void setJumlahHadir(int jumlahHadir) {
        this.jumlahHadir = jumlahHadir;
    }

    public int getJumlahSakit() {
        return jumlahSakit;
    }

    public void setJumlahSakit(int jumlahSakit) {
        this.jumlahSakit = jumlahSakit;
    }

    public int getJumlahIzin() {
        return jumlahIzin;
    }

    public void setJumlahIzin(int jumlahIzin) {
        this.jumlahIzin = jumlahIzin;
    }

    public int getJumlahAlpa() {
        return jumlahAlpa;
    }

    public void setJumlahAlpa(int jumlahAlpa) {
        this.jumlahAlpa = jumlahAlpa;
    }

    public String getCatatanWaliKelas() {
        return catatanWaliKelas;
    }

    public void setCatatanWaliKelas(String catatanWaliKelas) {
        this.catatanWaliKelas = catatanWaliKelas;
    }
}
