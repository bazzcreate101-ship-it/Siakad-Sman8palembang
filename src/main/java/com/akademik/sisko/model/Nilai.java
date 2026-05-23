package com.akademik.sisko.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nilai", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"siswa_id", "mata_pelajaran_id"})
})
public class Nilai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siswa_id", nullable = false)
    private Siswa siswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mata_pelajaran_id", nullable = false)
    private MataPelajaran mataPelajaran;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kelas_id", nullable = false)
    private Kelas kelas;

    @Column(nullable = false)
    private Double tugas;

    @Column(nullable = false)
    private Double uts;

    @Column(nullable = false)
    private Double uas;

    @Column(name = "nilai_akhir", nullable = false)
    private Double nilaiAkhir;

    // Constructors
    public Nilai() {
        this.tugas = 0.0;
        this.uts = 0.0;
        this.uas = 0.0;
        this.nilaiAkhir = 0.0;
    }

    public Nilai(Siswa siswa, MataPelajaran mataPelajaran, Kelas kelas, Double tugas, Double uts, Double uas) {
        this.siswa = siswa;
        this.mataPelajaran = mataPelajaran;
        this.kelas = kelas;
        this.tugas = tugas != null ? tugas : 0.0;
        this.uts = uts != null ? uts : 0.0;
        this.uas = uas != null ? uas : 0.0;
        hitungNilaiAkhir();
    }

    // Method to calculate final grade (Required PBO logic)
    public void hitungNilaiAkhir() {
        double t = this.tugas != null ? this.tugas : 0.0;
        double ut = this.uts != null ? this.uts : 0.0;
        double ua = this.uas != null ? this.uas : 0.0;
        this.nilaiAkhir = (0.3 * t) + (0.3 * ut) + (0.4 * ua);
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

    public MataPelajaran getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(MataPelajaran mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public Double getTugas() {
        return tugas;
    }

    public void setTugas(Double tugas) {
        this.tugas = tugas;
        hitungNilaiAkhir();
    }

    public Double getUts() {
        return uts;
    }

    public void setUts(Double uts) {
        this.uts = uts;
        hitungNilaiAkhir();
    }

    public Double getUas() {
        return uas;
    }

    public void setUas(Double uas) {
        this.uas = uas;
        hitungNilaiAkhir();
    }

    public Double getNilaiAkhir() {
        return nilaiAkhir;
    }

    public void setNilaiAkhir(Double nilaiAkhir) {
        this.nilaiAkhir = nilaiAkhir;
    }
}
