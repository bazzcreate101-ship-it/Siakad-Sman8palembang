package com.akademik.sisko.repository;

import com.akademik.sisko.model.Kelas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KelasRepository extends JpaRepository<Kelas, Long> {
    Optional<Kelas> findByNamaKelas(String namaKelas);
    // Pagination & case‑insensitive search on namaKelas
    Page<Kelas> findByNamaKelasContainingIgnoreCase(String keyword, Pageable pageable);
}
