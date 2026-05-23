package com.akademik.sisko.service;

import com.akademik.sisko.model.MataPelajaran;
import com.akademik.sisko.repository.MataPelajaranRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MataPelajaranService {

    private final MataPelajaranRepository mataPelajaranRepository;

    public MataPelajaranService(MataPelajaranRepository mataPelajaranRepository) {
        this.mataPelajaranRepository = mataPelajaranRepository;
    }

    public List<MataPelajaran> getAllMataPelajaran() {
        return mataPelajaranRepository.findAll();
    }

    public Optional<MataPelajaran> getMataPelajaranById(Long id) {
        return mataPelajaranRepository.findById(id);
    }

    @Transactional
    public MataPelajaran saveMataPelajaran(MataPelajaran mapel) {
        if (mapel.getId() == null && mataPelajaranRepository.findByKodeMapel(mapel.getKodeMapel()).isPresent()) {
            throw new IllegalArgumentException("Kode mata pelajaran sudah digunakan.");
        }
        return mataPelajaranRepository.save(mapel);
    }

    @Transactional
    public void deleteMataPelajaran(Long id) {
        mataPelajaranRepository.deleteById(id);
    }
}
