package com.akademik.sisko.service;

import com.akademik.sisko.model.Nilai;
import com.akademik.sisko.model.Rapor;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.repository.AbsensiRepository;
import com.akademik.sisko.repository.NilaiRepository;
import com.akademik.sisko.repository.SiswaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RaporService {

    private final SiswaRepository siswaRepository;
    private final NilaiRepository nilaiRepository;
    private final AbsensiRepository absensiRepository;

    public RaporService(SiswaRepository siswaRepository,
                        NilaiRepository nilaiRepository,
                        AbsensiRepository absensiRepository) {
        this.siswaRepository = siswaRepository;
        this.nilaiRepository = nilaiRepository;
        this.absensiRepository = absensiRepository;
    }

    public Optional<Rapor> generateRapor(Long siswaId) {
        Optional<Siswa> siswaOpt = siswaRepository.findById(siswaId);
        if (siswaOpt.isEmpty()) {
            return Optional.empty();
        }
        Siswa siswa = siswaOpt.get();
        List<Nilai> listNilai = nilaiRepository.findBySiswaId(siswaId);
        
        int hadir = absensiRepository.countBySiswaIdAndStatus(siswaId, "HADIR");
        int sakit = absensiRepository.countBySiswaIdAndStatus(siswaId, "SAKIT");
        int izin = absensiRepository.countBySiswaIdAndStatus(siswaId, "IZIN");
        int alpa = absensiRepository.countBySiswaIdAndStatus(siswaId, "ALPA");

        Rapor rapor = new Rapor(siswa, listNilai, hadir, sakit, izin, alpa);
        return Optional.of(rapor);
    }

    public Optional<Rapor> cariRapor(String nisn, LocalDate tanggalLahir) {
        Optional<Siswa> siswaOpt = siswaRepository.findByNisnAndCalonSiswaTanggalLahir(nisn, tanggalLahir);
        if (siswaOpt.isEmpty()) {
            return Optional.empty();
        }
        return generateRapor(siswaOpt.get().getId());
    }
}
