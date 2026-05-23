package com.akademik.sisko.config;

import com.akademik.sisko.model.*;
import com.akademik.sisko.repository.KelasRepository;
import com.akademik.sisko.repository.MataPelajaranRepository;
import com.akademik.sisko.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final KelasRepository kelasRepository;
    private final MataPelajaranRepository mataPelajaranRepository;

    public DatabaseSeeder(UserRepository userRepository,
                          KelasRepository kelasRepository,
                          MataPelajaranRepository mataPelajaranRepository) {
        this.userRepository = userRepository;
        this.kelasRepository = kelasRepository;
        this.mataPelajaranRepository = mataPelajaranRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed Users (Admin & Guru)
        if (userRepository.count() == 0) {
            // Admin
            Admin admin = new Admin("admin", "admin123", "Administrator Sistem");
            userRepository.save(admin);

            // Guru 1
            Guru guru1 = new Guru("guru1", "guru123", "Budi Santoso, S.Pd.", "198001012005011001");
            userRepository.save(guru1);

            // Guru 2
            Guru guru2 = new Guru("guru2", "guru123", "Siti Aminah, M.Pd.", "198502022010022002");
            userRepository.save(guru2);

            System.out.println(">>> DatabaseSeeder: Users seeded successfully (admin, guru1, guru2).");
        }

        // 2. Seed Kelas
        if (kelasRepository.count() == 0) {
            Kelas kelas1 = new Kelas("X-A", 10);
            kelasRepository.save(kelas1);

            Kelas kelas2 = new Kelas("X-B", 10);
            kelasRepository.save(kelas2);

            Kelas kelas3 = new Kelas("XI-A", 11);
            kelasRepository.save(kelas3);

            System.out.println(">>> DatabaseSeeder: Classes seeded successfully (X-A, X-B, XI-A).");
        }

        // 3. Seed Mata Pelajaran
        if (mataPelajaranRepository.count() == 0) {
            MataPelajaran mapel1 = new MataPelajaran("MAT", "Matematika");
            mataPelajaranRepository.save(mapel1);

            MataPelajaran mapel2 = new MataPelajaran("BIN", "Bahasa Indonesia");
            mataPelajaranRepository.save(mapel2);

            MataPelajaran mapel3 = new MataPelajaran("IPA", "Ilmu Pengetahuan Alam");
            mataPelajaranRepository.save(mapel3);

            System.out.println(">>> DatabaseSeeder: Subjects seeded successfully (Matematika, Bahasa Indonesia, IPA).");
        }
    }
}
