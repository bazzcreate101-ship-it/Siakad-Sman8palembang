package com.akademik.sisko.controller;

import com.akademik.sisko.model.*;
import com.akademik.sisko.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PPDBService ppdbService;
    private final SiswaService siswaService;
    private final GuruService guruService;
    private final KelasService kelasService;
    private final MataPelajaranService mataPelajaranService;
    private final JadwalService jadwalService;

    public AdminController(PPDBService ppdbService,
                           SiswaService siswaService,
                           GuruService guruService,
                           KelasService kelasService,
                           MataPelajaranService mataPelajaranService,
                           JadwalService jadwalService) {
        this.ppdbService = ppdbService;
        this.siswaService = siswaService;
        this.guruService = guruService;
        this.kelasService = kelasService;
        this.mataPelajaranService = mataPelajaranService;
        this.jadwalService = jadwalService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalPendaftar", ppdbService.getAllPendaftar().size());
        model.addAttribute("totalSiswa", siswaService.getAllSiswa().size());
        model.addAttribute("totalGuru", guruService.getAllGuru().size());
        model.addAttribute("totalKelas", kelasService.getAllKelas().size());
        model.addAttribute("totalMapel", mataPelajaranService.getAllMataPelajaran().size());
        return "admin/dashboard";
    }

    // ==========================================
    // 1. PPDB MANAGEMENT
    // ==========================================
    @GetMapping("/ppdb")
    public String ppdbList(Model model) {
        model.addAttribute("listPendaftar", ppdbService.getAllPendaftar());
        model.addAttribute("pengaturan", ppdbService.getPengaturan());
        return "admin/ppdb-list";
    }

    @PostMapping("/ppdb/verifikasi")
    public String ppdbVerifikasi(@RequestParam Long id, @RequestParam String status, Model model) {
        try {
            ppdbService.verifikasi(id, status);
            return "redirect:/admin/ppdb?success=true";
        } catch (Exception e) {
            return "redirect:/admin/ppdb?error=" + e.getMessage();
        }
    }

    @GetMapping("/ppdb/pengaturan")
    public String ppdbPengaturanForm(Model model) {
        model.addAttribute("pengaturan", ppdbService.getPengaturan());
        return "admin/ppdb-pengaturan";
    }

    @PostMapping("/ppdb/pengaturan")
    public String ppdbPengaturanSave(@ModelAttribute PengaturanPPDB pengaturan, Model model) {
        try {
            ppdbService.updatePengaturan(pengaturan);
            return "redirect:/admin/ppdb?success_config=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pengaturan", pengaturan);
            return "admin/ppdb-pengaturan";
        }
    }

    // ==========================================
    // 2. SISWA MANAGEMENT
    // ==========================================
    @GetMapping("/siswa")
    public String siswaList(Model model) {
        model.addAttribute("listSiswa", siswaService.getAllSiswa());
        model.addAttribute("listKelas", kelasService.getAllKelas());
        return "admin/siswa-list";
    }

    @PostMapping("/siswa/set-kelas")
    public String siswaSetKelas(@RequestParam Long siswaId, @RequestParam(required = false) Long kelasId) {
        siswaService.updateSiswaKelas(siswaId, kelasId);
        return "redirect:/admin/siswa?success=true";
    }

    @PostMapping("/siswa/delete")
    public String siswaDelete(@RequestParam Long id) {
        siswaService.deleteSiswa(id);
        return "redirect:/admin/siswa?deleted=true";
    }

    // ==========================================
    // 3. GURU MANAGEMENT
    // ==========================================
    @GetMapping("/guru")
    public String guruList(Model model) {
        model.addAttribute("listGuru", guruService.getAllGuru());
        return "admin/guru-list";
    }

    @GetMapping("/guru/tambah")
    public String guruTambahForm(Model model) {
        model.addAttribute("guru", new Guru());
        return "admin/guru-form";
    }

    @PostMapping("/guru/tambah")
    public String guruTambahSave(@ModelAttribute Guru guru, Model model) {
        try {
            guruService.saveGuru(guru);
            return "redirect:/admin/guru?success=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("guru", guru);
            return "admin/guru-form";
        }
    }

    @GetMapping("/guru/edit/{id}")
    public String guruEditForm(@PathVariable Long id, Model model) {
        Optional<Guru> guruOpt = guruService.getGuruById(id);
        if (guruOpt.isPresent()) {
            model.addAttribute("guru", guruOpt.get());
            return "admin/guru-form";
        }
        return "redirect:/admin/guru?error=not_found";
    }

    @PostMapping("/guru/edit/{id}")
    public String guruEditSave(@PathVariable Long id, @ModelAttribute Guru guru, Model model) {
        try {
            guru.setId(id);
            guruService.saveGuru(guru);
            return "redirect:/admin/guru?updated=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("guru", guru);
            return "admin/guru-form";
        }
    }

    @PostMapping("/guru/delete")
    public String guruDelete(@RequestParam Long id) {
        guruService.deleteGuru(id);
        return "redirect:/admin/guru?deleted=true";
    }

    // ==========================================
    // 4. KELAS MANAGEMENT
    // ==========================================
    @GetMapping("/kelas")
    public String kelasList(Model model) {
        model.addAttribute("listKelas", kelasService.getAllKelas());
        model.addAttribute("kelas", new Kelas());
        return "admin/kelas-list";
    }

    @PostMapping("/kelas/tambah")
    public String kelasTambah(@ModelAttribute Kelas kelas, Model model) {
        try {
            kelasService.saveKelas(kelas);
            return "redirect:/admin/kelas?success=true";
        } catch (Exception e) {
            return "redirect:/admin/kelas?error=" + e.getMessage();
        }
    }

    @PostMapping("/kelas/delete")
    public String kelasDelete(@RequestParam Long id) {
        try {
            kelasService.deleteKelas(id);
            return "redirect:/admin/kelas?deleted=true";
        } catch (Exception e) {
            return "redirect:/admin/kelas?error=Gagal menghapus kelas karena masih memiliki relasi siswa/jadwal.";
        }
    }

    // ==========================================
    // 5. MAPEL MANAGEMENT
    // ==========================================
    @GetMapping("/mapel")
    public String mapelList(Model model) {
        model.addAttribute("listMapel", mataPelajaranService.getAllMataPelajaran());
        model.addAttribute("mapel", new MataPelajaran());
        return "admin/mapel-list";
    }

    @PostMapping("/mapel/tambah")
    public String mapelTambah(@ModelAttribute MataPelajaran mapel, Model model) {
        try {
            mataPelajaranService.saveMataPelajaran(mapel);
            return "redirect:/admin/mapel?success=true";
        } catch (Exception e) {
            return "redirect:/admin/mapel?error=" + e.getMessage();
        }
    }

    @PostMapping("/mapel/delete")
    public String mapelDelete(@RequestParam Long id) {
        try {
            mataPelajaranService.deleteMataPelajaran(id);
            return "redirect:/admin/mapel?deleted=true";
        } catch (Exception e) {
            return "redirect:/admin/mapel?error=Gagal menghapus karena masih digunakan di jadwal/nilai.";
        }
    }

    // ==========================================
    // 6. JADWAL MANAGEMENT
    // ==========================================
    @GetMapping("/jadwal")
    public String jadwalList(Model model) {
        model.addAttribute("listJadwal", jadwalService.getAllJadwal());
        return "admin/jadwal-list";
    }

    @GetMapping("/jadwal/tambah")
    public String jadwalTambahForm(Model model) {
        model.addAttribute("listKelas", kelasService.getAllKelas());
        model.addAttribute("listGuru", guruService.getAllGuru());
        model.addAttribute("listMapel", mataPelajaranService.getAllMataPelajaran());
        return "admin/jadwal-form";
    }

    @PostMapping("/jadwal/tambah")
    public String jadwalTambahSave(@RequestParam Long kelasId,
                                   @RequestParam Long guruId,
                                   @RequestParam Long mataPelajaranId,
                                   @RequestParam String hari,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime jamMulai,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime jamSelesai,
                                   Model model) {
        try {
            Kelas kelas = kelasService.getKelasById(kelasId)
                    .orElseThrow(() -> new IllegalArgumentException("Kelas tidak ditemukan."));
            Guru guru = guruService.getGuruById(guruId)
                    .orElseThrow(() -> new IllegalArgumentException("Guru tidak ditemukan."));
            MataPelajaran mapel = mataPelajaranService.getMataPelajaranById(mataPelajaranId)
                    .orElseThrow(() -> new IllegalArgumentException("Mapel tidak ditemukan."));

            JadwalPelajaran jadwal = new JadwalPelajaran(kelas, guru, mapel, hari, jamMulai, jamSelesai);
            jadwalService.saveJadwal(jadwal);
            return "redirect:/admin/jadwal?success=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listKelas", kelasService.getAllKelas());
            model.addAttribute("listGuru", guruService.getAllGuru());
            model.addAttribute("listMapel", mataPelajaranService.getAllMataPelajaran());
            return "admin/jadwal-form";
        }
    }

    @PostMapping("/jadwal/delete")
    public String jadwalDelete(@RequestParam Long id) {
        jadwalService.deleteJadwal(id);
        return "redirect:/admin/jadwal?deleted=true";
    }
}
