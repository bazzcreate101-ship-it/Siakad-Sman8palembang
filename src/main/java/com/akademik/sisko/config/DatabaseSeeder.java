package com.akademik.sisko.config;

import com.akademik.sisko.model.*;
import com.akademik.sisko.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final KelasRepository kelasRepository;
    private final MataPelajaranRepository mataPelajaranRepository;
    private final SiswaRepository siswaRepository;
    private final CalonSiswaRepository calonSiswaRepository;
    private final JadwalRepository jadwalRepository;
    private final AbsensiRepository absensiRepository;
    private final NilaiRepository nilaiRepository;
    private final PengaturanPPDBRepository pengaturanPPDBRepository;

    public DatabaseSeeder(UserRepository userRepository,
                          KelasRepository kelasRepository,
                          MataPelajaranRepository mataPelajaranRepository,
                          SiswaRepository siswaRepository,
                          CalonSiswaRepository calonSiswaRepository,
                          JadwalRepository jadwalRepository,
                          AbsensiRepository absensiRepository,
                          NilaiRepository nilaiRepository,
                          PengaturanPPDBRepository pengaturanPPDBRepository) {
        this.userRepository = userRepository;
        this.kelasRepository = kelasRepository;
        this.mataPelajaranRepository = mataPelajaranRepository;
        this.siswaRepository = siswaRepository;
        this.calonSiswaRepository = calonSiswaRepository;
        this.jadwalRepository = jadwalRepository;
        this.absensiRepository = absensiRepository;
        this.nilaiRepository = nilaiRepository;
        this.pengaturanPPDBRepository = pengaturanPPDBRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Seed Subjects (Mata Pelajaran)
        List<MataPelajaran> mapelList = new ArrayList<>();
        if (mataPelajaranRepository.count() == 0) {
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("MAT", "Matematika")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("BIN", "Bahasa Indonesia")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("FIS", "Fisika")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("KIM", "Kimia")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("BIO", "Biologi")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("AGI", "Agama Islam")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("SEJ", "Sejarah")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("ING", "Bahasa Inggris")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("INF", "Informatika")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("SEN", "Seni Budaya")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("PEN", "Penjasorkes")));
            mapelList.add(mataPelajaranRepository.save(new MataPelajaran("GEO", "Geografi")));
            System.out.println(">>> DatabaseSeeder: 12 Subjects seeded successfully.");
        } else {
            mapelList = mataPelajaranRepository.findAll();
        }

        // 2. Seed Classes (Kelas)
        List<Kelas> classesList = new ArrayList<>();
        if (kelasRepository.count() == 0) {
            classesList.add(kelasRepository.save(new Kelas("X-A", 10)));
            classesList.add(kelasRepository.save(new Kelas("X-B", 10)));
            classesList.add(kelasRepository.save(new Kelas("X-C", 10)));
            classesList.add(kelasRepository.save(new Kelas("XI-A", 11)));
            classesList.add(kelasRepository.save(new Kelas("XI-B", 11)));
            classesList.add(kelasRepository.save(new Kelas("XI-C", 11)));
            classesList.add(kelasRepository.save(new Kelas("XII-A", 12)));
            classesList.add(kelasRepository.save(new Kelas("XII-B", 12)));
            classesList.add(kelasRepository.save(new Kelas("XII-C", 12)));
            System.out.println(">>> DatabaseSeeder: 9 Classes (X-A to XII-C) seeded successfully.");
        } else {
            classesList = kelasRepository.findAll();
        }

        // 3. Seed Users (Admin & Guru)
        List<Guru> teachersList = new ArrayList<>();
        if (userRepository.count() <= 1) {
            // Seed Admin if not exists
            if (userRepository.count() == 0) {
                Admin admin = new Admin("admin", "admin123", "Administrator Sistem");
                userRepository.save(admin);
            }

            // Seed 12 Teachers matching subjects
            teachersList.add((Guru) userRepository.save(new Guru("ahmaddahlan", "guru123", "Drs. Ahmad Dahlan, M.Si.", "197203151998031002")));
            teachersList.add((Guru) userRepository.save(new Guru("sriwahyuni", "guru123", "Dra. Sri Wahyuni", "197508222002122001")));
            teachersList.add((Guru) userRepository.save(new Guru("bambang", "guru123", "Dr. Bambang Setiawan, M.Pd.", "196811051994031003")));
            teachersList.add((Guru) userRepository.save(new Guru("rinaamelia", "guru123", "Rina Amelia, S.Pd.", "198304122009042002")));
            teachersList.add((Guru) userRepository.save(new Guru("jokosusilo", "guru123", "Joko Susilo, M.Si.", "197809182006041002")));
            teachersList.add((Guru) userRepository.save(new Guru("yusuf", "guru123", "H. M. Yusuf, S.Ag.", "197012251999031001")));
            teachersList.add((Guru) userRepository.save(new Guru("ekoprasetyo", "guru123", "Eko Prasetyo, S.Pd.", "198501302011011002")));
            teachersList.add((Guru) userRepository.save(new Guru("fitriani", "guru123", "Fitriani, M.Pd.", "198205162008012003")));
            teachersList.add((Guru) userRepository.save(new Guru("budiraharjo", "guru123", "Budi Raharjo, S.Kom.", "198810242014021001")));
            teachersList.add((Guru) userRepository.save(new Guru("dewilestari", "guru123", "Dewi Lestari, S.Sn.", "198007082006042003")));
            teachersList.add((Guru) userRepository.save(new Guru("hendrawijaya", "guru123", "Hendra Wijaya, S.Pd.", "198606202010121001")));
            teachersList.add((Guru) userRepository.save(new Guru("kartikaputri", "guru123", "Kartika Putri, S.Pd.", "198909152015032002")));

            System.out.println(">>> DatabaseSeeder: 12 Teachers seeded successfully.");
        } else {
            List<User> allUsers = userRepository.findAll();
            for (User u : allUsers) {
                if (u instanceof Guru) {
                    teachersList.add((Guru) u);
                }
            }
        }

        // 4. Seed 72 Students (Siswa & CalonSiswa)
        List<Siswa> activeStudents = new ArrayList<>();
        if (siswaRepository.count() == 0) {
            String[] namaSiswaLaki = {
                "Aditya Pratama", "Ahmad Ridho", "Bayu Saputra", "Budi Wijaya", "Candra Wijaya",
                "Dedi Setiawan", "Farhan Ramadhan", "Gilang Permana", "Hendra Lesmana", "Kevin Sanjaya",
                "Muhammad Fajar", "Rahmat Hidayat", "Rizky Ramadhan", "Sandi Perkasa", "Teguh Santoso",
                "Wahyu Hidayat", "Yudi Hermawan", "Zulfikar Ali", "Naufal Azhar", "Lutfi Hakim",
                "Ivan Sebastian", "Gilang Dirga", "Fajar Nugraha", "Dwi Cahyono", "Edi Wibowo",
                "Harianto Wijaya", "Lukman Hakim", "Mulyadi Pratama", "Ridho Pangestu", "Slamet Riyadi",
                "Taufik Hidayat", "Wawan Setiawan", "Yanto Sugiarto", "Dimas Anggara", "Eko Prasetyo",
                "Guntur Bumi"
            };

            String[] namaSiswaPerempuan = {
                "Adinda Putri", "Anisa Rahma", "Bella Citra", "Bunga Lestari", "Citra Kirana",
                "Dewi Sartika", "Dian Lestari", "Fitri Handayani", "Gita Savitri", "Indah Permatasari",
                "Kartika Sari", "Larasati Putri", "Maya Indah", "Nanda Amelia", "Novianti Putri",
                "Olivia Zalianty", "Putri Ayu", "Ratna Sari", "Rina Wijaya", "Sari Rahmawati",
                "Selvi Ananda", "Tiara Andini", "Wati Setiawati", "Wulandari Putri", "Yuliana Safitri",
                "Zaskia Adya", "Erni Sulistowati", "Febriana Dwipuji", "Grace Sitorus", "Irma Suryani",
                "Lilis Suryani", "Mega Utami", "Olivia Wijaya", "Ratna Galih", "Tri Utami",
                "Vina Amelia"
            };

            String[] alamatPalembang = {
                "Jl. Sudirman No. 12, Palembang",
                "Jl. Merdeka No. 45, Palembang",
                "Jl. Kolonel Atmo No. 89, Palembang",
                "Jl. Basuki Rahmat No. 10, Palembang",
                "Jl. R. Soekamto No. 55, Palembang",
                "Jl. Veteran No. 34, Palembang",
                "Jl. Demang Lebar Daun No. 120, Palembang",
                "Jl. Kenten Indah No. 8, Palembang",
                "Jl. Plaju No. 15, Palembang",
                "Jl. Jakabaring No. 3, Palembang",
                "Jl. Kamboja No. 27, Palembang",
                "Jl. Mayor Salim Batubara No. 6, Palembang",
                "Jl. Jenderal Ahmad Yani No. 104, Palembang",
                "Jl. Veteran No. 12B, Palembang",
                "Jl. Letkol Iskandar No. 88, Palembang",
                "Jl. Radial No. 4, Palembang"
            };

            String[] namaBapak = {
                "Heri Setiawan", "Bambang Wijaya", "Rudi Hartono", "Agus Susanto", "Hendra Saputra",
                "Mulyono", "Slamet", "Djoko Prasetyo", "Andi", "Toni Kusuma"
            };

            int baseNisn = 812345001;
            long baseNik = 1671011234560001L;
            int studentCount = 0;

            for (int classIndex = 0; classIndex < classesList.size(); classIndex++) {
                Kelas kelas = classesList.get(classIndex);
                int tingkat = kelas.getTingkat();
                int birthYear = (tingkat == 10) ? 2010 : ((tingkat == 11) ? 2009 : 2008);

                for (int i = 0; i < 4; i++) {
                    // Male Student
                    String namaL = namaSiswaLaki[classIndex * 4 + i];
                    String nikL = String.valueOf(baseNik++);
                    String nisnL = "0" + baseNisn++;
                    LocalDate birthDateL = LocalDate.of(birthYear, ((studentCount % 12) + 1), ((studentCount % 28) + 1));
                    String alamatL = alamatPalembang[studentCount % alamatPalembang.length];
                    String bapakL = namaBapak[studentCount % namaBapak.length];
                    String telpL = "0812" + String.format("%08d", (studentCount * 987654L) % 100000000);

                    CalonSiswa csL = new CalonSiswa(nikL, namaL, birthDateL, "Laki-laki", alamatL, bapakL, telpL, "DITERIMA");
                    calonSiswaRepository.save(csL);

                    Siswa sL = new Siswa();
                    sL.setNisn(nisnL);
                    sL.setNamaLengkap(namaL);
                    sL.setCalonSiswa(csL);
                    sL.setKelas(kelas);
                    sL.setStatus("AKTIF");
                    activeStudents.add(siswaRepository.save(sL));
                    studentCount++;

                    // Female Student
                    String namaP = namaSiswaPerempuan[classIndex * 4 + i];
                    String nikP = String.valueOf(baseNik++);
                    String nisnP = "0" + baseNisn++;
                    LocalDate birthDateP = LocalDate.of(birthYear, ((studentCount % 12) + 1), ((studentCount % 28) + 1));
                    String alamatP = alamatPalembang[studentCount % alamatPalembang.length];
                    String bapakP = namaBapak[studentCount % namaBapak.length];
                    String telpP = "0812" + String.format("%08d", (studentCount * 987654L) % 100000000);

                    CalonSiswa csP = new CalonSiswa(nikP, namaP, birthDateP, "Perempuan", alamatP, bapakP, telpP, "DITERIMA");
                    calonSiswaRepository.save(csP);

                    Siswa sP = new Siswa();
                    sP.setNisn(nisnP);
                    sP.setNamaLengkap(namaP);
                    sP.setCalonSiswa(csP);
                    sP.setKelas(kelas);
                    sP.setStatus("AKTIF");
                    activeStudents.add(siswaRepository.save(sP));
                    studentCount++;
                }
            }
            System.out.println(">>> DatabaseSeeder: 72 Active Students seeded successfully.");
        } else {
            activeStudents = siswaRepository.findAll();
        }

        // 5. Seed Schedules (Jadwal Pelajaran)
        if (jadwalRepository.count() == 0 && classesList.size() >= 9 && teachersList.size() >= 12 && mapelList.size() >= 12) {
            // Class X-A
            jadwalRepository.save(new JadwalPelajaran(classesList.get(0), teachersList.get(0), mapelList.get(0), "Senin", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(0), teachersList.get(1), mapelList.get(1), "Senin", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(0), teachersList.get(2), mapelList.get(2), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(0), teachersList.get(3), mapelList.get(3), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(0), teachersList.get(4), mapelList.get(4), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class X-B
            jadwalRepository.save(new JadwalPelajaran(classesList.get(1), teachersList.get(1), mapelList.get(1), "Senin", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(1), teachersList.get(0), mapelList.get(0), "Senin", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(1), teachersList.get(3), mapelList.get(3), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(1), teachersList.get(2), mapelList.get(2), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(1), teachersList.get(5), mapelList.get(5), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class X-C
            jadwalRepository.save(new JadwalPelajaran(classesList.get(2), teachersList.get(2), mapelList.get(2), "Senin", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(2), teachersList.get(3), mapelList.get(3), "Senin", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(2), teachersList.get(0), mapelList.get(0), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(2), teachersList.get(1), mapelList.get(1), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(2), teachersList.get(8), mapelList.get(8), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XI-A
            jadwalRepository.save(new JadwalPelajaran(classesList.get(3), teachersList.get(4), mapelList.get(4), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(3), teachersList.get(5), mapelList.get(5), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(3), teachersList.get(6), mapelList.get(6), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(3), teachersList.get(7), mapelList.get(7), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(3), teachersList.get(8), mapelList.get(8), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XI-B
            jadwalRepository.save(new JadwalPelajaran(classesList.get(4), teachersList.get(5), mapelList.get(5), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(4), teachersList.get(4), mapelList.get(4), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(4), teachersList.get(7), mapelList.get(7), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(4), teachersList.get(6), mapelList.get(6), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(4), teachersList.get(9), mapelList.get(9), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XI-C
            jadwalRepository.save(new JadwalPelajaran(classesList.get(5), teachersList.get(6), mapelList.get(6), "Selasa", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(5), teachersList.get(7), mapelList.get(7), "Selasa", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(5), teachersList.get(4), mapelList.get(4), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(5), teachersList.get(5), mapelList.get(5), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(5), teachersList.get(10), mapelList.get(10), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XII-A
            jadwalRepository.save(new JadwalPelajaran(classesList.get(6), teachersList.get(9), mapelList.get(9), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(6), teachersList.get(10), mapelList.get(10), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(6), teachersList.get(11), mapelList.get(11), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(6), teachersList.get(0), mapelList.get(0), "Kamis", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(6), teachersList.get(1), mapelList.get(1), "Jumat", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XII-B
            jadwalRepository.save(new JadwalPelajaran(classesList.get(7), teachersList.get(10), mapelList.get(10), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(7), teachersList.get(9), mapelList.get(9), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(7), teachersList.get(0), mapelList.get(0), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(7), teachersList.get(11), mapelList.get(11), "Kamis", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(7), teachersList.get(7), mapelList.get(7), "Jumat", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            // Class XII-C
            jadwalRepository.save(new JadwalPelajaran(classesList.get(8), teachersList.get(11), mapelList.get(11), "Rabu", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(8), teachersList.get(0), mapelList.get(0), "Rabu", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(8), teachersList.get(9), mapelList.get(9), "Kamis", LocalTime.of(7, 30), LocalTime.of(9, 0)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(8), teachersList.get(10), mapelList.get(10), "Kamis", LocalTime.of(9, 0), LocalTime.of(10, 30)));
            jadwalRepository.save(new JadwalPelajaran(classesList.get(8), teachersList.get(6), mapelList.get(6), "Jumat", LocalTime.of(7, 30), LocalTime.of(9, 0)));

            System.out.println(">>> DatabaseSeeder: Schedules seeded successfully.");
        }

        // 6. Seed Attendance (Absensi) - 5 days
        if (absensiRepository.count() == 0 && !activeStudents.isEmpty()) {
            LocalDate today = LocalDate.now();
            for (int d = 1; d <= 5; d++) {
                LocalDate tgl = today.minusDays(d);
                if (tgl.getDayOfWeek().getValue() == 6 || tgl.getDayOfWeek().getValue() == 7) {
                    tgl = tgl.minusDays(2);
                }
                for (int sIndex = 0; sIndex < activeStudents.size(); sIndex++) {
                    Siswa s = activeStudents.get(sIndex);
                    String status = "HADIR";
                    int val = (sIndex + d) % 30;
                    if (val == 0) {
                        status = "SAKIT";
                    } else if (val == 1) {
                        status = "IZIN";
                    } else if (val == 2) {
                        status = "ALPA";
                    }
                    absensiRepository.save(new Absensi(s, s.getKelas(), tgl, status));
                }
            }
            System.out.println(">>> DatabaseSeeder: Attendance data seeded successfully.");
        }

        // 7. Seed Grades (Nilai) - 3 subjects per student
        if (nilaiRepository.count() == 0 && !activeStudents.isEmpty() && mapelList.size() >= 8) {
            for (int sIndex = 0; sIndex < activeStudents.size(); sIndex++) {
                Siswa s = activeStudents.get(sIndex);
                // Math (Index 0)
                double tugasM = 80.0 + (sIndex % 15);
                double utsM = 75.0 + (sIndex % 18);
                double uasM = 78.0 + (sIndex % 17);
                nilaiRepository.save(new Nilai(s, mapelList.get(0), s.getKelas(), tugasM, utsM, uasM));

                // Indo (Index 1)
                double tugasI = 85.0 + (sIndex % 10);
                double utsI = 80.0 + (sIndex % 12);
                double uasI = 82.0 + (sIndex % 11);
                nilaiRepository.save(new Nilai(s, mapelList.get(1), s.getKelas(), tugasI, utsI, uasI));

                // Eng (Index 7)
                double tugasE = 78.0 + (sIndex % 13);
                double utsE = 82.0 + (sIndex % 14);
                double uasE = 80.0 + (sIndex % 15);
                nilaiRepository.save(new Nilai(s, mapelList.get(7), s.getKelas(), tugasE, utsE, uasE));
            }
            System.out.println(">>> DatabaseSeeder: Grades data seeded successfully.");
        }

        // 8. Seed PPDB Candidates (MENUNGGU_VERIFIKASI & DITOLAK)
        if (calonSiswaRepository.count() <= 72) {
            String[] pendaftarLaki = {
                "Hendro Wibowo", "Dian Pratama", "Rizki Saputra", "Agus Setiawan", "Bambang Hermawan", "Deni Cahyadi", "Ari Wibowo", "Tio Setiadi"
            };
            String[] pendaftarPerempuan = {
                "Silvia Ningsih", "Rini Astuti", "Eka Saputri", "Sri Lestari", "Mega Lestari", "Anita Sari", "Lisa Amanda", "Tanti Wijaya"
            };

            long extraBaseNik = 1671011234560100L;
            int counter = 0;

            // 8 MENUNGGU_VERIFIKASI
            for (int i = 0; i < 4; i++) {
                // Male
                String namaL = pendaftarLaki[i];
                String nikL = String.valueOf(extraBaseNik++);
                LocalDate birthDateL = LocalDate.of(2010, 5, 10 + i);
                if (calonSiswaRepository.findByNik(nikL).isEmpty()) {
                    calonSiswaRepository.save(new CalonSiswa(nikL, namaL, birthDateL, "Laki-laki", "Jl. Sudirman No. 100, Palembang", "Bapak Siswanto", "0812717100" + counter++, "MENUNGGU_VERIFIKASI"));
                } else {
                    counter++;
                }

                // Female
                String namaP = pendaftarPerempuan[i];
                String nikP = String.valueOf(extraBaseNik++);
                LocalDate birthDateP = LocalDate.of(2010, 6, 12 + i);
                if (calonSiswaRepository.findByNik(nikP).isEmpty()) {
                    calonSiswaRepository.save(new CalonSiswa(nikP, namaP, birthDateP, "Perempuan", "Jl. Demang Lebar Daun No. 22, Palembang", "Bapak Rudianto", "0812717100" + counter++, "MENUNGGU_VERIFIKASI"));
                } else {
                    counter++;
                }
            }

            // 7 DITOLAK
            for (int i = 4; i < 8; i++) {
                if (i == 7) {
                    // Female
                    String namaP = pendaftarPerempuan[i - 1];
                    String nikP = String.valueOf(extraBaseNik++);
                    LocalDate birthDateP = LocalDate.of(2010, 7, 15);
                    if (calonSiswaRepository.findByNik(nikP).isEmpty()) {
                        calonSiswaRepository.save(new CalonSiswa(nikP, namaP, birthDateP, "Perempuan", "Jl. Plaju No. 88, Palembang", "Bapak Suparjo", "0812717100" + counter++, "DITOLAK"));
                    } else {
                        counter++;
                    }
                    break;
                }
                // Male
                String namaL = pendaftarLaki[i];
                String nikL = String.valueOf(extraBaseNik++);
                LocalDate birthDateL = LocalDate.of(2010, 8, 20 + i);
                if (calonSiswaRepository.findByNik(nikL).isEmpty()) {
                    calonSiswaRepository.save(new CalonSiswa(nikL, namaL, birthDateL, "Laki-laki", "Jl. Veteran No. 5, Palembang", "Bapak Hardi", "0812717100" + counter++, "DITOLAK"));
                } else {
                    counter++;
                }

                // Female
                String namaP = pendaftarPerempuan[i];
                String nikP = String.valueOf(extraBaseNik++);
                LocalDate birthDateP = LocalDate.of(2010, 9, 22 + i);
                if (calonSiswaRepository.findByNik(nikP).isEmpty()) {
                    calonSiswaRepository.save(new CalonSiswa(nikP, namaP, birthDateP, "Perempuan", "Jl. Basuki Rahmat No. 74, Palembang", "Bapak Wahyudi", "0812717100" + counter++, "DITOLAK"));
                } else {
                    counter++;
                }
            }
            System.out.println(">>> DatabaseSeeder: Additional 15 PPDB candidates seeded successfully.");
        }

        // 9. PPDB Config (PengaturanPPDB)
        if (pengaturanPPDBRepository.count() == 0) {
            PengaturanPPDB defaultSettings = new PengaturanPPDB(
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(30),
                    LocalDate.now().plusDays(35),
                    100
            );
            pengaturanPPDBRepository.save(defaultSettings);
            System.out.println(">>> DatabaseSeeder: PPDB Settings seeded successfully.");
        }
    }
}

