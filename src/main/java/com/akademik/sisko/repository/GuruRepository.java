package com.akademik.sisko.repository;

import com.akademik.sisko.model.Guru;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GuruRepository extends JpaRepository<Guru, Long> {
    Optional<Guru> findByNip(String nip);
}
