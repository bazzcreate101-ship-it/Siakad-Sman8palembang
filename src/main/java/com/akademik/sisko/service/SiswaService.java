package com.akademik.sisko.service;

import com.akademik.sisko.model.Kelas;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.model.Nilai;
import com.akademik.sisko.model.Absensi;
import com.akademik.sisko.repository.KelasRepository;
import com.akademik.sisko.repository.SiswaRepository;
import com.akademik.sisko.repository.NilaiRepository;
import com.akademik.sisko.repository.AbsensiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SiswaService {

    private final SiswaRepository siswaRepository;
    private final KelasRepository kelasRepository;
    private final NilaiRepository nilaiRepository;
    private final AbsensiRepository absensiRepository;

    public SiswaService(SiswaRepository siswaRepository,
                        KelasRepository kelasRepository,
                        NilaiRepository nilaiRepository,
                        AbsensiRepository absensiRepository) {
        this.siswaRepository = siswaRepository;
        this.kelasRepository = kelasRepository;
        this.nilaiRepository = nilaiRepository;
        this.absensiRepository = absensiRepository;
    }

    public List<Siswa> getAllSiswa() {
        return siswaRepository.findAll();
    }

    public Optional<Siswa> getSiswaById(Long id) {
        return siswaRepository.findById(id);
    }

    public Optional<Siswa> getSiswaByNisn(String nisn) {
        return siswaRepository.findByNisn(nisn);
    }

    @Transactional
    public Siswa saveSiswa(Siswa siswa) {
        return siswaRepository.save(siswa);
    }

    @Transactional
    public void updateSiswaKelas(Long siswaId, Long kelasId) {
        Siswa siswa = siswaRepository.findById(siswaId)
                .orElseThrow(() -> new IllegalArgumentException("Siswa tidak ditemukan."));
        
        if (kelasId != null) {
            Kelas kelas = kelasRepository.findById(kelasId)
                    .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));
            siswa.setKelas(kelas);
            
            // Sync existing grades with the new class
            List<Nilai> nilaiList = nilaiRepository.findBySiswaId(siswaId);
            for (Nilai n : nilaiList) {
                n.setKelas(kelas);
                nilaiRepository.save(n);
            }
            
            // Sync existing attendance with the new class
            List<Absensi> absensiList = absensiRepository.findBySiswaId(siswaId);
            for (Absensi a : absensiList) {
                a.setKelas(kelas);
                absensiRepository.save(a);
            }
        } else {
            siswa.setKelas(null);
        }
        siswaRepository.save(siswa);
    }

    public List<Siswa> getSiswaByKelas(Long kelasId) {
        return siswaRepository.findByKelasId(kelasId);
    }

    @Transactional
    public void deleteSiswa(Long id) {
        // Cascade delete student's grades
        List<Nilai> nilaiList = nilaiRepository.findBySiswaId(id);
        nilaiRepository.deleteAll(nilaiList);

        // Cascade delete student's attendance records
        List<Absensi> absensiList = absensiRepository.findBySiswaId(id);
        absensiRepository.deleteAll(absensiList);

        siswaRepository.deleteById(id);
    }
}
