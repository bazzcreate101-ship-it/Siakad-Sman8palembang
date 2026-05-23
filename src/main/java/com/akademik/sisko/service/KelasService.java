package com.akademik.sisko.service;

import com.akademik.sisko.model.Kelas;
import com.akademik.sisko.repository.KelasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class KelasService {

    private final KelasRepository kelasRepository;

    public KelasService(KelasRepository kelasRepository) {
        this.kelasRepository = kelasRepository;
    }

    public List<Kelas> getAllKelas() {
        return kelasRepository.findAll();
    }

    public Optional<Kelas> getKelasById(Long id) {
        return kelasRepository.findById(id);
    }

    @Transactional
    public Kelas saveKelas(Kelas kelas) {
        if (kelas.getId() == null && kelasRepository.findByNamaKelas(kelas.getNamaKelas()).isPresent()) {
            throw new IllegalArgumentException("Nama kelas sudah digunakan.");
        }
        return kelasRepository.save(kelas);
    }

    @Transactional
    public void deleteKelas(Long id) {
        kelasRepository.deleteById(id);
    }
}
