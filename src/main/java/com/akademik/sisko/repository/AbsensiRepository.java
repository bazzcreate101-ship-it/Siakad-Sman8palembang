package com.akademik.sisko.repository;

import com.akademik.sisko.model.Absensi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsensiRepository extends JpaRepository<Absensi, Long> {
    List<Absensi> findBySiswaId(Long siswaId);
    List<Absensi> findByKelasIdAndTanggal(Long kelasId, LocalDate tanggal);
    Optional<Absensi> findBySiswaIdAndTanggal(Long siswaId, LocalDate tanggal);
    
    // Count methods for report card attendance summary
    int countBySiswaIdAndStatus(Long siswaId, String status);
}
