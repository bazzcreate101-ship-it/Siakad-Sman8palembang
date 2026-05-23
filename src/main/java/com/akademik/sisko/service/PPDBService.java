package com.akademik.sisko.service;

import com.akademik.sisko.model.CalonSiswa;
import com.akademik.sisko.model.PengaturanPPDB;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.repository.CalonSiswaRepository;
import com.akademik.sisko.repository.PengaturanPPDBRepository;
import com.akademik.sisko.repository.SiswaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PPDBService {

    private final CalonSiswaRepository calonSiswaRepository;
    private final SiswaRepository siswaRepository;
    private final PengaturanPPDBRepository pengaturanPPDBRepository;

    // Constructor Injection (Encapsulation / Dependency Injection)
    public PPDBService(CalonSiswaRepository calonSiswaRepository,
                       SiswaRepository siswaRepository,
                       PengaturanPPDBRepository pengaturanPPDBRepository) {
        this.calonSiswaRepository = calonSiswaRepository;
        this.siswaRepository = siswaRepository;
        this.pengaturanPPDBRepository = pengaturanPPDBRepository;
    }

    public List<CalonSiswa> getAllPendaftar() {
        return calonSiswaRepository.findAll();
    }

    public Optional<CalonSiswa> getPendaftarById(Long id) {
        return calonSiswaRepository.findById(id);
    }

    public Optional<CalonSiswa> getPendaftarByNik(String nik) {
        return calonSiswaRepository.findByNik(nik);
    }

    @Transactional
    public CalonSiswa daftar(CalonSiswa calonSiswa) {
        if (calonSiswaRepository.findByNik(calonSiswa.getNik()).isPresent()) {
            throw new IllegalArgumentException("NIK sudah terdaftar di sistem.");
        }
        
        if (!isPPDBBuka()) {
            throw new IllegalStateException("Pendaftaran PPDB sedang ditutup.");
        }

        calonSiswa.setStatusPendaftaran("MENUNGGU_VERIFIKASI");
        return calonSiswaRepository.save(calonSiswa);
    }

    @Transactional
    public void verifikasi(Long id, String status) {
        CalonSiswa pendaftar = calonSiswaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Calon siswa tidak ditemukan."));

        if (!"MENUNGGU_VERIFIKASI".equals(pendaftar.getStatusPendaftaran())) {
            throw new IllegalStateException("Pendaftar sudah diproses sebelumnya.");
        }

        if ("DITERIMA".equalsIgnoreCase(status)) {
            // Check quota
            PengaturanPPDB config = getPengaturan();
            long totalDiterima = calonSiswaRepository.findAll().stream()
                    .filter(c -> "DITERIMA".equals(c.getStatusPendaftaran()))
                    .count();

            if (totalDiterima >= config.getKuota()) {
                throw new IllegalStateException("Kuota PPDB sudah penuh!");
            }

            pendaftar.setStatusPendaftaran("DITERIMA");
            calonSiswaRepository.save(pendaftar);

            // Automatically create active student (Siswa)
            String nisn = generateUniqueNisn();
            Siswa siswa = new Siswa();
            siswa.setNisn(nisn);
            siswa.setNamaLengkap(pendaftar.getNamaLengkap());
            siswa.setCalonSiswa(pendaftar);
            siswa.setStatus("AKTIF");
            siswaRepository.save(siswa);
        } else if ("DITOLAK".equalsIgnoreCase(status)) {
            pendaftar.setStatusPendaftaran("DITOLAK");
            calonSiswaRepository.save(pendaftar);
        } else {
            throw new IllegalArgumentException("Status verifikasi tidak valid.");
        }
    }

    public PengaturanPPDB getPengaturan() {
        List<PengaturanPPDB> list = pengaturanPPDBRepository.findAll();
        if (list.isEmpty()) {
            // Create default settings if empty
            PengaturanPPDB defaultSettings = new PengaturanPPDB(
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(30),
                    LocalDate.now().plusDays(35),
                    50
            );
            return pengaturanPPDBRepository.save(defaultSettings);
        }
        return list.get(0);
    }

    @Transactional
    public PengaturanPPDB updatePengaturan(PengaturanPPDB newSettings) {
        PengaturanPPDB existing = getPengaturan();
        existing.setTanggalBuka(newSettings.getTanggalBuka());
        existing.setTanggalTutup(newSettings.getTanggalTutup());
        existing.setTanggalPengumuman(newSettings.getTanggalPengumuman());
        existing.setKuota(newSettings.getKuota());
        return pengaturanPPDBRepository.save(existing);
    }

    public boolean isPPDBBuka() {
        PengaturanPPDB config = getPengaturan();
        LocalDate today = LocalDate.now();
        return !today.isBefore(config.getTanggalBuka()) && !today.isAfter(config.getTanggalTutup());
    }

    private String generateUniqueNisn() {
        String newNisn;
        do {
            newNisn = String.format("%010d", (long) (Math.random() * 10000000000L));
        } while (siswaRepository.findByNisn(newNisn).isPresent());
        return newNisn;
    }
}
