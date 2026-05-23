package com.akademik.sisko.controller;

import com.akademik.sisko.model.CalonSiswa;
import com.akademik.sisko.model.Rapor;
import com.akademik.sisko.service.PPDBService;
import com.akademik.sisko.service.RaporService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class HomeController {

    private final PPDBService ppdbService;
    private final RaporService raporService;

    public HomeController(PPDBService ppdbService, RaporService raporService) {
        this.ppdbService = ppdbService;
        this.raporService = raporService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("isPPDBBuka", ppdbService.isPPDBBuka());
        return "index";
    }

    @GetMapping("/ppdb/daftar")
    public String ppdbDaftarForm(Model model) {
        if (!ppdbService.isPPDBBuka()) {
            return "redirect:/?error=ppdb_closed";
        }
        model.addAttribute("calonSiswa", new CalonSiswa());
        return "ppdb-daftar";
    }

    @PostMapping("/ppdb/daftar")
    public String ppdbDaftarSubmit(@ModelAttribute CalonSiswa calonSiswa, Model model) {
        try {
            ppdbService.daftar(calonSiswa);
            return "redirect:/ppdb/status?nik=" + calonSiswa.getNik() + "&success=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("calonSiswa", calonSiswa);
            return "ppdb-daftar";
        }
    }

    @GetMapping("/ppdb/status")
    public String ppdbStatus(@RequestParam(value = "nik", required = false) String nik, Model model) {
        if (nik != null && !nik.isBlank()) {
            Optional<CalonSiswa> pendaftar = ppdbService.getPendaftarByNik(nik.trim());
            if (pendaftar.isPresent()) {
                model.addAttribute("pendaftar", pendaftar.get());
                model.addAttribute("pengaturan", ppdbService.getPengaturan());
            } else {
                model.addAttribute("error", "Data pendaftaran dengan NIK tersebut tidak ditemukan.");
            }
        }
        return "ppdb-status";
    }

    @GetMapping("/rapor/cari")
    public String raporCari(@RequestParam(value = "nisn", required = false) String nisn,
                            @RequestParam(value = "tanggalLahir", required = false) 
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggalLahir,
                            Model model) {
        if (nisn != null && !nisn.isBlank() && tanggalLahir != null) {
            Optional<Rapor> raporOpt = raporService.cariRapor(nisn.trim(), tanggalLahir);
            if (raporOpt.isPresent()) {
                model.addAttribute("rapor", raporOpt.get());
                return "rapor-detail";
            } else {
                model.addAttribute("error", "NISN atau Tanggal Lahir salah, atau rapor belum diinput.");
            }
        }
        return "rapor-search";
    }
}
