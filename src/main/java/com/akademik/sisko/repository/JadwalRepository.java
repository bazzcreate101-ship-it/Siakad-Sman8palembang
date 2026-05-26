package com.akademik.sisko.repository;

import com.akademik.sisko.model.JadwalPelajaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JadwalRepository extends JpaRepository<JadwalPelajaran, Long> {
    List<JadwalPelajaran> findByKelasId(Long kelasId);
    List<JadwalPelajaran> findByGuruId(Long guruId);
    // Pagination for all jadwal entries
    Page<JadwalPelajaran> findAll(Pageable pageable);
    // Simple search on hari (case‑insensitive)
    Page<JadwalPelajaran> findByHariContainingIgnoreCase(String keyword, Pageable pageable);
}
