package com.akademik.sisko.service;

import com.akademik.sisko.model.JadwalPelajaran;
import com.akademik.sisko.repository.JadwalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JadwalService {

    private final JadwalRepository jadwalRepository;

    public JadwalService(JadwalRepository jadwalRepository) {
        this.jadwalRepository = jadwalRepository;
    }

    public List<JadwalPelajaran> getAllJadwal() {
        return jadwalRepository.findAll();
    }

    public Optional<JadwalPelajaran> getJadwalById(Long id) {
        return jadwalRepository.findById(id);
    }

    public List<JadwalPelajaran> getJadwalByKelas(Long kelasId) {
        return jadwalRepository.findByKelasId(kelasId);
    }

    public List<JadwalPelajaran> getJadwalByGuru(Long guruId) {
        return jadwalRepository.findByGuruId(guruId);
    }

    @Transactional
    public JadwalPelajaran saveJadwal(JadwalPelajaran jadwal) {
        // Here we could add validation to prevent teacher or class time collision,
        // but keeping it simple as per requirement.
        return jadwalRepository.save(jadwal);
    }

    @Transactional
    public void deleteJadwal(Long id) {
        jadwalRepository.deleteById(id);
    }
}
