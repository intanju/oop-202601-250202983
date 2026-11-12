# Class Diagram Aset-Pinjam (modul core)

Diagram ini mengikuti kode yang ada di `core/src/main/java`. Isinya fokus ke bagian domain yang sudah dipakai: model, kontrak, denda, katalog, jadwal, varian, dan exception. Kelas yang masih berupa kerangka untuk minggu 10-11 (`LayananDenda`, `AsetFactory`, dan DAO) belum saya gambar supaya diagram tidak menampilkan sesuatu yang belum benar-benar jalan.

Versi gambar: [`class-diagram.svg`](class-diagram.svg).

```mermaid
classDiagram
    namespace kontrak {
        class DapatDipinjam {
            <<interface>>
            +bisaDipinjam() boolean
        }

        class AturanDenda {
            <<interface>>
            +hitung(Aset, int) long
        }
    }

    namespace model {
        class Aset {
            <<abstract>>
            -String kode
            -String nama
            -StatusAset status
            -Kondisi kondisi
            +Aset(String, String)
            +getKode() String
            +getNama() String
            +setNama(String) void
            +getStatus() StatusAset
            +setStatus(StatusAset) void
            +getKondisi() Kondisi
            +setKondisi(Kondisi) void
            +bisaDipinjam() boolean
            +getJenis() JenisAset
            +hitungDenda(int) long
        }

        class Ruang {
            -int kapasitas
            +Ruang(String, String, int)
            +getKapasitas() int
            +getJenis() JenisAset
            +hitungDenda(int) long
        }

        class Laptop {
            -String spesifikasi
            -String nomorInventaris
            +Laptop(String, String, String, String)
            +getSpesifikasi() String
            +getNomorInventaris() String
            +getJenis() JenisAset
            +hitungDenda(int) long
        }

        class AlatLab {
            -String laboratorium
            -TingkatRisiko tingkatRisiko
            +AlatLab(String, String, String, TingkatRisiko)
            +getLaboratorium() String
            +getTingkatRisiko() TingkatRisiko
            +getJenis() JenisAset
            +hitungDenda(int) long
        }

        class Peminjam {
            <<record>>
            +id() String
            +nama() String
        }

        class Peminjaman {
            -String id
            -Peminjam peminjam
            -Aset aset
            -LocalDateTime mulai
            -LocalDateTime selesai
            -StatusPeminjaman status
            +Peminjaman(String, Peminjam, Aset, LocalDateTime, LocalDateTime)
            +getId() String
            +getPeminjam() Peminjam
            +getAset() Aset
            +getMulai() LocalDateTime
            +getSelesai() LocalDateTime
            +getStatus() StatusPeminjaman
            +setStatusDariPenyimpanan(StatusPeminjaman) void
            +ubahStatus(StatusPeminjaman) void
        }

        class StatusAset {
            <<enumeration>>
            TERSEDIA
            DIPINJAM
            RUSAK
        }

        class Kondisi {
            <<enumeration>>
            BAIK
            RUSAK_RINGAN
            RUSAK_BERAT
        }

        class JenisAset {
            <<enumeration>>
            RUANG
            LAPTOP
            ALAT_LAB
        }

        class TingkatRisiko {
            <<enumeration>>
            RENDAH
            SEDANG
            TINGGI
        }

        class StatusPeminjaman {
            <<enumeration>>
            DIAJUKAN
            DISETUJUI
            DITOLAK
            DIBATALKAN
            DIPINJAM
            TERLAMBAT
            DIKEMBALIKAN
        }
    }

    namespace denda {
        class DendaFlat {
            +hitung(Aset, int) long
        }

        class DendaProgresif {
            +hitung(Aset, int) long
        }
    }

    namespace katalog {
        class KatalogAset {
            -List~Aset~ daftar
            +tambah(Aset) void
            +semua() List~Aset~
            +cari(String) List~Aset~
            +cari(String, JenisAset) List~Aset~
        }
    }

    namespace jadwal {
        class JadwalPeminjaman {
            -List~Peminjaman~ daftar
            -int kuotaLaptop
            +JadwalPeminjaman(int)
            #kuotaLaptop() int
            #daftarMutable() List~Peminjaman~
            +tambah(Peminjaman) void
            +bentrok(Peminjaman) boolean
            +semuaUrutMulai() List~Peminjaman~
            +perAset() Map
            +hapus(String) boolean
            +ajukan(Peminjaman) void
        }
    }

    namespace config {
        class Varian {
            <<final>>
            +String nim
            +String nama
            +int dendaPerHari
            +int batasDurasiJam
            +int kuotaLaptop
            +aktif() Varian
            +dariNim(String, String) Varian
        }
    }

    namespace exception {
        class AsetPinjamException
        class DataAksesException
        class KuotaHabisException
        class PeminjamanBentrokException
        class StatusTidakSahException
    }

    Aset ..|> DapatDipinjam
    Ruang --|> Aset
    Laptop --|> Aset
    AlatLab --|> Aset

    Aset --> "1" StatusAset
    Aset --> "1" Kondisi
    Aset ..> JenisAset
    AlatLab --> "1" TingkatRisiko

    DendaFlat ..|> AturanDenda
    DendaProgresif ..|> AturanDenda
    AturanDenda ..> Aset
    Ruang ..> Varian
    Laptop ..> Varian
    AlatLab ..> Varian

    KatalogAset o-- "0..*" Aset
    JadwalPeminjaman o-- "0..*" Peminjaman
    Peminjaman --> "1" Peminjam
    Peminjaman --> "1" Aset
    Peminjaman --> "1" StatusPeminjaman

    DataAksesException --|> AsetPinjamException
    KuotaHabisException --|> AsetPinjamException
    PeminjamanBentrokException --|> AsetPinjamException
    StatusTidakSahException --|> AsetPinjamException
```

## Keterangan relasi

- `KatalogAset` dan `JadwalPeminjaman` berelasi **aggregation** (`o--`). Objek `Aset` dan `Peminjaman` dibuat di luar lalu dimasukkan lewat `tambah(...)`, jadi objeknya tetap bisa hidup tanpa wadah tersebut.
- `Peminjaman` ke `Aset` dan `Peminjam` adalah **association** dengan multiplicity 1. Konstruktor `Peminjaman` menolak null, jadi satu peminjaman wajib punya satu aset dan satu peminjam.
- Di kode ini belum ada **composition** murni. `status` dan `kondisi` pada `Aset` cuma enum, bukan objek terpisah yang dimiliki `Aset`. Relasi ke `Peminjam` juga bukan composition karena `Peminjam` bisa dipakai di banyak peminjaman.
- `Ruang`, `Laptop`, dan `AlatLab` punya **dependency** ke `Varian` karena `hitungDenda` membaca `Varian.aktif().dendaPerHari`.
- `DendaFlat` dan `DendaProgresif` mengimplementasikan `AturanDenda`, lalu `AturanDenda` bergantung pada `Aset` karena method `hitung` menerima parameter `Aset`.
- Tipe asli `JadwalPeminjaman.perAset()` adalah `Map<String, List<Peminjaman>>`. Di Mermaid saya tulis `Map` saja supaya sintaks diagramnya tidak rumit.
- Method `bentrok` dan `ajukan` sudah ada di kode sebagai kerangka minggu 7 dan 9, jadi tetap saya cantumkan di diagram.