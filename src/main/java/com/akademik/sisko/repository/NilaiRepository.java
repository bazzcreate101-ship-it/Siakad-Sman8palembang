package com.akademik.sisko.repository;

import com.akademik.sisko.model.Nilai;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NilaiRepository extends JpaRepository<Nilai, Long> {
    List<Nilai> findBySiswaId(Long siswaId);
    List<Nilai> findByKelasId(Long kelasId);
    List<Nilai> findByKelasIdAndMataPelajaranId(Long kelasId, Long mataPelajaranId);
    Optional<Nilai> findBySiswaIdAndMataPelajaranId(Long siswaId, Long mataPelajaranId);
}
