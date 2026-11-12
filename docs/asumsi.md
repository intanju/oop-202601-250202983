# Asumsi dan Keputusan Desain

Isi bagian ini sendiri. Satu entri per keputusan. Dinilai dari kejelasan alasan dan konsistensi dengan kode dan tes Anda,
bukan dari jawaban "benar".

Format tiap entri:

```
## A-NN: judul singkat
- Pertanyaan: ...
- Keputusan: ...
- Alasan: ...
- Dampak ke kode: ... (kelas/metode)
- Tes yang membuktikan: ... (nama tes di folder mahasiswa/)
```

## Aturan ambigu dari docs/aturan-bisnis.md

### A-01: Batas Waktu Bentrok
- Pertanyaan: Apa definisi bentrok di batas waktu? Peminjaman 09:00-11:00 dan 11:00-13:00 pada aset yang sama: bentrok atau tidak?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-02: Perhitungan Denda untuk Sisa Jam/Menit
- Pertanyaan: Bagaimana denda dihitung bila terlambat 1 menit atau 25 jam? (Sistem hanya mengenal hari bulat pada hitungDenda.)
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-03: Peminjaman Aset yang Sama Dua Kali di Hari yang Sama
- Pertanyaan: Bolehkah satu peminjam meminjam aset yang sama dua kali pada hari yang sama?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-04: Tanggung Jawab Kerusakan Tanpa Saksi
- Pertanyaan: Siapa menanggung biaya bila kondisi aset saat kembali lebih buruk dan tidak ada saksi?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-05: Penanganan Pengajuan Disetujui jika Aset Rusak Mendadak
- Pertanyaan: Apa yang terjadi pada pengajuan yang sudah disetujui bila aset dinyatakan rusak sebelum serah terima?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-06: Sanksi Pembatalan Mendadak & Pembatalan Status DIAJUKAN
- Pertanyaan: Apakah pembatalan mendadak (kurang dari 1 jam sebelum mulai) dikenai sanksi? Bolehkah DIAJUKAN langsung DIBATALKAN?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

### A-07: Status Ditolak/Dibatalkan terhadap Jadwal dan Kuota
- Pertanyaan: Apakah peminjaman berstatus DITOLAK atau DIBATALKAN masih menempati jadwal? Apakah DIAJUKAN dihitung dalam kuota?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)


## Asumsi lain
Tambahkan di sini keputusan lain yang Anda ambil: rumus denda progresif, perubahan skema database, format error API, dan seterusnya.

### AL-01: Normalisasi Kode
- Pertanyaan: Apakah string input untuk kode dan nama aset perlu dinormalisasi (menggunakan `trim()`) sebelum disimpan?
- Keputusan: Ya, menggunakan fungsi `trim()` untuk menghilangkan spasi yang tidak disengaja pada awal atau akhir input, sementara untuk pengecekan kekosongan data tetap menggunakan method `isBlank()`.
- Alasan: untuk menghindari ketidaksesuaian data akibat kesalahan pengetikan, seperti adanya spasi berlebih.
- Dampak ke kode: Penambahan fungsi trim pada konstruktor Aset dan setter setNama
- Tes yang membuktikan: `core/src/test/java/id/ac/upb/asetpinjam/Minggu02AsetTest.java`.

### AL-02: Rumus Denda Progresif
- Pertanyaan: Bagaimana denda progresif dihitung agar makin berat bila makin lama terlambat?
- Keputusan: Denda dasar (flat) ditambah persentase bertingkat: hari 1-2 tambahan 0%, hari 3-5 tambahan 15%, hari 6-10 tambahan 30%, hari 11 ke atas tambahan 60%.
- Alasan: Ada masa tenggang 2 hari, lalu tambahan naik per tingkat sehingga keterlambatan lama terasa lebih berat.
- Dampak ke kode: `DendaProgresif.hitung`
- Tes yang membuktikan: `core/src/test/java/id/ac/upb/asetpinjam/Minggu05AbstraksiTest.java`