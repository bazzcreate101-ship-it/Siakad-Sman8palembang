package com.akademik.sisko.service;

import com.akademik.sisko.model.Kelas;
import com.akademik.sisko.model.MataPelajaran;
import com.akademik.sisko.model.Nilai;
import com.akademik.sisko.model.Siswa;
import com.akademik.sisko.repository.KelasRepository;
import com.akademik.sisko.repository.MataPelajaranRepository;
import com.akademik.sisko.repository.NilaiRepository;
import com.akademik.sisko.repository.SiswaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NilaiService {

    private final NilaiRepository nilaiRepository;
    private final SiswaRepository siswaRepository;
    private final MataPelajaranRepository mataPelajaranRepository;
    private final KelasRepository kelasRepository;

    public NilaiService(NilaiRepository nilaiRepository,
                        SiswaRepository siswaRepository,
                        MataPelajaranRepository mataPelajaranRepository,
                        KelasRepository kelasRepository) {
        this.nilaiRepository = nilaiRepository;
        this.siswaRepository = siswaRepository;
        this.mataPelajaranRepository = mataPelajaranRepository;
        this.kelasRepository = kelasRepository;
    }

    public List<Nilai> getNilaiBySiswa(Long siswaId) {
        return nilaiRepository.findBySiswaId(siswaId);
    }

    public List<Nilai> getNilaiByKelasAndMapel(Long kelasId, Long mapelId) {
        return nilaiRepository.findByKelasIdAndMataPelajaranId(kelasId, mapelId);
    }

    @Transactional
    public Nilai simpanNilai(Long siswaId, Long mapelId, Long kelasId, Double tugas, Double uts, Double uas) {
        Siswa siswa = siswaRepository.findById(siswaId)
                .orElseThrow(() -> new IllegalArgumentException("Siswa tidak ditemukan."));
        MataPelajaran mapel = mataPelajaranRepository.findById(mapelId)
                .orElseThrow(() -> new IllegalArgumentException("Mata pelajaran tidak ditemukan."));
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));

        Optional<Nilai> existing = nilaiRepository.findBySiswaIdAndMataPelajaranId(siswaId, mapelId);
        Nilai nilai;
        if (existing.isPresent()) {
            nilai = existing.get();
            nilai.setTugas(tugas);
            nilai.setUts(uts);
            nilai.setUas(uas);
            nilai.setKelas(kelas); // Update class just in case
        } else {
            nilai = new Nilai(siswa, mapel, kelas, tugas, uts, uas);
        }

        nilai.hitungNilaiAkhir(); // Explicitly call grade calculation
        return nilaiRepository.save(nilai);
    }
}
