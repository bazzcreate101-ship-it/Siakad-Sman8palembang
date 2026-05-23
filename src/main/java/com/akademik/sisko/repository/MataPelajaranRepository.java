package com.akademik.sisko.repository;

import com.akademik.sisko.model.MataPelajaran;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MataPelajaranRepository extends JpaRepository<MataPelajaran, Long> {
    Optional<MataPelajaran> findByKodeMapel(String kodeMapel);
}
