package com.akademik.sisko.service;

import com.akademik.sisko.model.Kelas;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.repository.KelasRepository;
import com.akademik.sisko.repository.SiswaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SiswaService {

    private final SiswaRepository siswaRepository;
    private final KelasRepository kelasRepository;

    public SiswaService(SiswaRepository siswaRepository, KelasRepository kelasRepository) {
        this.siswaRepository = siswaRepository;
        this.kelasRepository = kelasRepository;
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
        siswaRepository.deleteById(id);
    }
}
