package com.akademik.sisko.service;

import com.akademik.sisko.model.Absensi;
import com.akademik.sisko.model.Kelas;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.repository.AbsensiRepository;
import com.akademik.sisko.repository.KelasRepository;
import com.akademik.sisko.repository.SiswaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AbsensiService {

    private final AbsensiRepository absensiRepository;
    private final SiswaRepository siswaRepository;
    private final KelasRepository kelasRepository;

    public java.util.Map<String, Integer> getRekapAbsensi(Long siswaId) {
        java.util.Map<String, Integer> rekap = new java.util.HashMap<>();
        rekap.put("HADIR", absensiRepository.countBySiswaIdAndStatus(siswaId, "HADIR"));
        rekap.put("SAKIT", absensiRepository.countBySiswaIdAndStatus(siswaId, "SAKIT"));
        rekap.put("IZIN", absensiRepository.countBySiswaIdAndStatus(siswaId, "IZIN"));
        rekap.put("ALPA", absensiRepository.countBySiswaIdAndStatus(siswaId, "ALPA"));
        return rekap;
    }

    public AbsensiService(AbsensiRepository absensiRepository,
                          SiswaRepository siswaRepository,
                          KelasRepository kelasRepository) {
        this.absensiRepository = absensiRepository;
        this.siswaRepository = siswaRepository;
        this.kelasRepository = kelasRepository;
    }

    public List<Absensi> getAbsensiByKelasAndTanggal(Long kelasId, LocalDate tanggal) {
        return absensiRepository.findByKelasIdAndTanggal(kelasId, tanggal);
    }

    @Transactional
    public void simpanAbsensi(Long kelasId, LocalDate tanggal, Map<Long, String> statusMap) {
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));

        for (Map.Entry<Long, String> entry : statusMap.entrySet()) {
            Long siswaId = entry.getKey();
            String status = entry.getValue();

            Siswa siswa = siswaRepository.findById(siswaId)
                    .orElseThrow(() -> new IllegalArgumentException("Siswa tidak ditemukan: " + siswaId));

            // Check if record already exists for this student on this day
            Optional<Absensi> existing = absensiRepository.findBySiswaIdAndTanggal(siswaId, tanggal);
            Absensi absensi;
            if (existing.isPresent()) {
                absensi = existing.get();
                absensi.setStatus(status);
            } else {
                absensi = new Absensi(siswa, kelas, tanggal, status);
            }
            absensiRepository.save(absensi);
        }
    }
}
