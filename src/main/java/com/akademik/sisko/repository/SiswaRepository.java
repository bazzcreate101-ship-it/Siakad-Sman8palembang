package com.akademik.sisko.repository;

import com.akademik.sisko.model.Siswa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SiswaRepository extends JpaRepository<Siswa, Long> {
    Optional<Siswa> findByNisn(String nisn);
    List<Siswa> findByKelasId(Long kelasId);
    Optional<Siswa> findByNisnAndCalonSiswaTanggalLahir(String nisn, LocalDate tanggalLahir);
    // Pagination & case‑insensitive search on namaLengkap
    Page<Siswa> findByNamaLengkapContainingIgnoreCase(String keyword, Pageable pageable);
}
