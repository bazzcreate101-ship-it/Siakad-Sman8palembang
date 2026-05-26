package com.akademik.sisko.repository;

import com.akademik.sisko.model.Guru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuruRepository extends JpaRepository<Guru, Long> {
    Optional<Guru> findByNip(String nip);
    // Pagination & case‑insensitive search on namaLengkap
    Page<Guru> findByNamaLengkapContainingIgnoreCase(String keyword, Pageable pageable);
}
