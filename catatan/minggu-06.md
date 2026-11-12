# Catatan Minggu 06: UML dan SOLID

## Yang saya kerjakan

Saya merapikan tiga berkas di `docs/uml`: class diagram, sequence diagram pengajuan, dan `solid.md`. Class diagram saya samakan dengan kode yang ada di `core/src/main/java`, mulai dari `Aset`, `Ruang`, `Laptop`, `AlatLab`, `Peminjaman`, sampai `KatalogAset` dan `JadwalPeminjaman`. Sequence diagram saya buat untuk alur `ajukan(...)`, lengkap dengan skenario sukses dan bentrok memakai fragmen `alt`.

## Keputusan desain

- Class diagram saya batasi ke domain inti yang sudah dipakai. `LayananDenda`, `AsetFactory`, dan DAO belum saya gambar karena masih berupa kerangka untuk minggu 10-11, jadi belum ada perilaku yang benar-benar jalan.
- Relasi `KatalogAset` dan `JadwalPeminjaman` ke objek yang disimpan saya gambar sebagai **aggregation**, karena objek `Aset` dan `Peminjaman` dibuat di luar lalu dimasukkan lewat `tambah(...)`. Di kode ini belum ada **composition** murni.
- Untuk SOLID, saya pilih OCP lewat `AturanDenda`, LSP lewat hierarki `Aset`, dan ISP lewat interface kecil `DapatDipinjam` dan `AturanDenda`. Ketiganya paling jelas dan tidak perlu mengubah kode.

## Kendala

Kendala utama saya adalah membedakan aggregation dan composition saat menggambar class diagram. Awalnya saya sempat ingin menggambar `Peminjaman` sebagai composition ke `Aset`, tetapi setelah melihat kode, `Aset` dibuat di luar dan hanya dioper lewat konstruktor, jadi relasinya association. Saya juga harus mengecek ulang tipe data dan visibility tiap field supaya diagram tidak berbeda dari kode.

## Penggunaan AI

- Alat yang dipakai: Claude
- Bagian yang dibantu AI: menyusun sintaks Mermaid untuk class diagram dan sequence diagram, merapikan kalimat `solid.md`
- Prompt utama: Analisis tugas minggu 6 dan cek apakah diagram yang ada sudah konsisten dengan kode.
- Yang saya ubah atau tolak dari saran AI: AI sempat menyarankan memasukkan semua kelas DAO, `AsetFactory`, dan `LayananDenda` ke class diagram. Saya tolak karena kelas-kelas itu masih kerangka dan belum dipakai, jadi malah bikin diagram tidak konsisten dengan kode. Saya juga menolak menambahkan relasi composition yang tidak ada di kode.
- Satu hal yang saya pahami lebih baik sekarang: aggregation itu objeknya bisa hidup sendiri tanpa wadahnya, sedangkan composition objeknya ikut mati bersama pemiliknya. Saya juga lebih paham kenapa OCP memudahkan penambahan aturan denda baru tanpa menyentuh class lama.

## Kuis Tanpa AI

### 1. Perbedaan aggregation dan composition

Aggregation dan composition sama-sama menunjukkan hubungan memiliki, tetapi tingkat kepemilikannya berbeda.
Aggregation merupakan hubungan ketika objek bagian masih bisa berdiri sendiri meskipun objek utamanya sudah tidak ada. Contohnya, KatalogAset menyimpan daftar Aset, tetapi objek Aset dibuat dari luar. Di UML menggunakan diamond kosong. Sedangkan Composition merupakan hubungan ketika objek bagian bergantung pada objek utamanya. Contohnya, Peminjaman membuat dan mengelola sendiri daftar detailnya. Di UML menggunakan diamond terisi.
Pada kode saya belum ada composition murni, sehingga hubungan KatalogAset dengan Aset dan JadwalPeminjaman dengan Peminjaman digambarkan sebagai aggregation.

### 2. Mengapa OCP memudahkan pengembangan

Karena OCP memungkinkan penambahan fitur tanpa perlu mengubah kode lama. Contohnya, saat ingin menambah turan denda, saya cukup membuat class baru seperti DendaProgresif yang mengimplementasikan AturanDenda, tanpa perlu mengubah DendaFlat atau class lainnya.

### 3. Mengapa DIP meningkatkan testability

DIP memudahkan pengujian karena kode bergantung pada interface, bukan class tertentu. Jadi, saat tes saya bisa menggunakan objek palsu sebagai pengganti implementasi asli, misalnya AsetDAO tanpa harus terhubung ke database MySQL. Hasilnya, pengujian lebih cepat dan mudah dilakukan.

## Dokumentasi
![Dokumentasi](Dokumentasi/06-doksli-1.png)