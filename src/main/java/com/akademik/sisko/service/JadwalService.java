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
        // Ensure start time is before end time
        if (jadwal.getJamMulai() == null || jadwal.getJamSelesai() == null 
                || !jadwal.getJamMulai().isBefore(jadwal.getJamSelesai())) {
            throw new IllegalArgumentException("Jam mulai harus lebih awal daripada jam selesai.");
        }

        // Validate scheduling collisions (teacher or class overlap)
        List<JadwalPelajaran> allJadwal = jadwalRepository.findAll();
        for (JadwalPelajaran jp : allJadwal) {
            // Skip if it's the exact same entry (useful if editing an existing schedule)
            if (jadwal.getId() != null && jadwal.getId().equals(jp.getId())) {
                continue;
            }

            // Check if on the same day
            if (jp.getHari().equalsIgnoreCase(jadwal.getHari())) {
                // Check time overlap: start1 < end2 AND start2 < end1
                boolean overlap = jadwal.getJamMulai().isBefore(jp.getJamSelesai()) 
                        && jp.getJamMulai().isBefore(jadwal.getJamSelesai());
                
                if (overlap) {
                    // Case 1: Class collision (same class cannot have overlapping schedules)
                    if (jp.getKelas().getId().equals(jadwal.getKelas().getId())) {
                        throw new IllegalArgumentException("Bentrok: Kelas " + jp.getKelas().getNamaKelas() 
                                + " sudah memiliki jadwal pelajaran lain pada hari dan jam tersebut (" 
                                + jp.getMataPelajaran().getNamaMapel() + " " + jp.getJamMulai() + " - " + jp.getJamSelesai() + ").");
                    }
                    
                    // Case 2: Teacher collision (same teacher cannot teach overlapping schedules)
                    if (jp.getGuru().getId().equals(jadwal.getGuru().getId())) {
                        throw new IllegalArgumentException("Bentrok: Guru " + jp.getGuru().getNamaLengkap() 
                                + " sudah mengajar di kelas lain (" + jp.getKelas().getNamaKelas() 
                                + ") pada hari dan jam tersebut (" + jp.getJamMulai() + " - " + jp.getJamSelesai() + ").");
                    }
                }
            }
        }
        return jadwalRepository.save(jadwal);
    }

    @Transactional
    public void deleteJadwal(Long id) {
        jadwalRepository.deleteById(id);
    }
}
