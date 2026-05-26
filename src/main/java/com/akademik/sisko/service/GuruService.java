package com.akademik.sisko.service;

import com.akademik.sisko.model.Guru;
import com.akademik.sisko.model.JadwalPelajaran;
import com.akademik.sisko.repository.GuruRepository;
import com.akademik.sisko.repository.JadwalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuruService {

    private final GuruRepository guruRepository;
    private final JadwalRepository jadwalRepository;

    public GuruService(GuruRepository guruRepository, JadwalRepository jadwalRepository) {
        this.guruRepository = guruRepository;
        this.jadwalRepository = jadwalRepository;
    }

    public List<Guru> getAllGuru() {
        return guruRepository.findAll();
    }

    public Optional<Guru> getGuruById(Long id) {
        return guruRepository.findById(id);
    }

    public Optional<Guru> getGuruByNip(String nip) {
        return guruRepository.findByNip(nip);
    }

    @Transactional
    public Guru saveGuru(Guru guru) {
        if (guru.getId() == null && guruRepository.findByNip(guru.getNip()).isPresent()) {
            throw new IllegalArgumentException("NIP sudah terdaftar.");
        }
        return guruRepository.save(guru);
    }

    @Transactional
    public void deleteGuru(Long id) {
        // Cascade delete teacher's class schedule entries
        List<JadwalPelajaran> jadwalList = jadwalRepository.findByGuruId(id);
        jadwalRepository.deleteAll(jadwalList);

        guruRepository.deleteById(id);
    }
}
