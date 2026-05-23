package com.akademik.sisko.repository;

import com.akademik.sisko.model.CalonSiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CalonSiswaRepository extends JpaRepository<CalonSiswa, Long> {
    Optional<CalonSiswa> findByNik(String nik);
}
