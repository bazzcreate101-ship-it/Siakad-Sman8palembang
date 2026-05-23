package com.akademik.sisko.controller;

import com.akademik.sisko.model.*;
import com.akademik.sisko.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/guru")
public class GuruController {

    private final JadwalService jadwalService;
    private final SiswaService siswaService;
    private final KelasService kelasService;
    private final MataPelajaranService mataPelajaranService;
    private final AbsensiService absensiService;
    private final NilaiService nilaiService;

    public GuruController(JadwalService jadwalService,
                          SiswaService siswaService,
                          KelasService kelasService,
                          MataPelajaranService mataPelajaranService,
                          AbsensiService absensiService,
                          NilaiService nilaiService) {
        this.jadwalService = jadwalService;
        this.siswaService = siswaService;
        this.kelasService = kelasService;
        this.mataPelajaranService = mataPelajaranService;
        this.absensiService = absensiService;
        this.nilaiService = nilaiService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        // Cast to Guru subclass (Inheritance polymorphism context)
        if (user instanceof Guru) {
            Guru guru = (Guru) user;
            List<JadwalPelajaran> jadwalGuru = jadwalService.getJadwalByGuru(guru.getId());
            model.addAttribute("listJadwal", jadwalGuru);
        }
        return "guru/dashboard";
    }

    // ==========================================
    // 1. ABSENSI SISWA
    // ==========================================
    @GetMapping("/absensi")
    public String absensiForm(Model model) {
        model.addAttribute("listKelas", kelasService.getAllKelas());
        return "guru/absensi-pilih";
    }

    @GetMapping("/absensi/isi")
    public String absensiIsi(@RequestParam Long kelasId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggal,
                             Model model) {
        Kelas kelas = kelasService.getKelasById(kelasId)
                .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));

        List<Siswa> listSiswa = siswaService.getSiswaByKelas(kelasId);
        List<Absensi> absensiList = absensiService.getAbsensiByKelasAndTanggal(kelasId, tanggal);

        // Map containing existing status to simplify rendering in Thymeleaf
        Map<Long, String> existingStatusMap = new HashMap<>();
        for (Absensi a : absensiList) {
            existingStatusMap.put(a.getSiswa().getId(), a.getStatus());
        }

        model.addAttribute("kelas", kelas);
        model.addAttribute("tanggal", tanggal);
        model.addAttribute("listSiswa", listSiswa);
        model.addAttribute("existingStatus", existingStatusMap);
        return "guru/absensi-isi";
    }

    @PostMapping("/absensi/simpan")
    public String absensiSimpan(@RequestParam Long kelasId,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggal,
                                @RequestParam Map<String, String> allParams) {
        
        Map<Long, String> statusMap = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("status_")) {
                Long siswaId = Long.parseLong(entry.getKey().substring(7));
                statusMap.put(siswaId, entry.getValue());
            }
        }

        absensiService.simpanAbsensi(kelasId, tanggal, statusMap);
        return "redirect:/guru/absensi?success=true";
    }

    // ==========================================
    // 2. NILAI SISWA
    // ==========================================
    @GetMapping("/nilai")
    public String nilaiForm(Model model) {
        model.addAttribute("listKelas", kelasService.getAllKelas());
        model.addAttribute("listMapel", mataPelajaranService.getAllMataPelajaran());
        return "guru/nilai-pilih";
    }

    @GetMapping("/nilai/isi")
    public String nilaiIsi(@RequestParam Long kelasId,
                           @RequestParam Long mapelId,
                           Model model) {
        Kelas kelas = kelasService.getKelasById(kelasId)
                .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));
        MataPelajaran mapel = mataPelajaranService.getMataPelajaranById(mapelId)
                .orElseThrow(() -> new IllegalArgumentException("Mapel tidak ditemukan."));

        List<Siswa> listSiswa = siswaService.getSiswaByKelas(kelasId);
        List<Nilai> listNilai = nilaiService.getNilaiByKelasAndMapel(kelasId, mapelId);

        // Map containing existing Nilai entities to simplify rendering in Thymeleaf
        Map<Long, Nilai> existingNilaiMap = new HashMap<>();
        for (Nilai n : listNilai) {
            existingNilaiMap.put(n.getSiswa().getId(), n);
        }

        model.addAttribute("kelas", kelas);
        model.addAttribute("mapel", mapel);
        model.addAttribute("listSiswa", listSiswa);
        model.addAttribute("existingNilai", existingNilaiMap);
        return "guru/nilai-isi";
    }

    @PostMapping("/nilai/simpan")
    public String nilaiSimpan(@RequestParam Long kelasId,
                              @RequestParam Long mapelId,
                              @RequestParam List<Long> siswaIds,
                              @RequestParam List<Double> tugas,
                              @RequestParam List<Double> uts,
                              @RequestParam List<Double> uas) {

        for (int i = 0; i < siswaIds.size(); i++) {
            Long siswaId = siswaIds.get(i);
            Double t = tugas.get(i);
            Double ut = uts.get(i);
            Double ua = uas.get(i);
            nilaiService.simpanNilai(siswaId, mapelId, kelasId, t, ut, ua);
        }
        return "redirect:/guru/nilai?success=true";
    }
}
