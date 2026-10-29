# Minggu 2: Struktur OOP: Class, Object, Encapsulation

## Yang saya kerjakan

Langkah pertama yang saya kerjakan yaitu membaca `docs/minggu-02.md`. Kemudian saya mengerjakan pada bagian `core/src/main/java/id/ac/upb/asetpinjam/model/Aset.java` dan melengkapi bagian yang bertanda `TODO minggu 2`.

Saya menambahkan kode pada bagian yang tertera `TODO`:

- Field Private
  Saya membuat semua field menjadi private. Field `kode` saya buat `final` karena tidak boleh berubah setelah objek dibuat. Nilai awal `status` dan `kondisi` saya isi langsung pada deklarasi field.

```java
private final String kode;
private String nama;
private StatusAset status = StatusAset.TERSEDIA;
private Kondisi kondisi = Kondisi.BAIK;
```

- Konstruktor
  Saya menambahkan validasi agar `kode` dan `nama` tidak boleh null atau kosong.

```java
if (kode == null || kode.isBlank()) {
    throw new IllegalArgumentException("kode tidak boleh null atau kosong");
}
if (nama == null || nama.isBlank()) {
    throw new IllegalArgumentException("nama tidak boleh null atau kosong");
}
this.kode = kode.trim();
this.nama = nama.trim();
```

- Getter dan Setter
  Getter saya buat untuk semua field sedangkan Setter hanya untuk `nama`, `status`, dan `kondisi`.`setNama` menolak nama kosong, sedangkan `setStatus` dan `setKondisi` menolak null.

Terakhir melakukan pengujian `.\scripts\uji.ps1`


## Keputusan desain

Saya memilih menormalisasi `kode` dan `nama` dengan `trim()` agar spasi di awal/akhir tidak tersimpan, Sedangkan untuk format huruf besar dibiarkan apa adanya sesuai input pengguna untuk menjaga keaslian data dan karena tidak ada aturan yang mewajibkan. Keputusan kode normalisasi saya catat di `docs/asumsi.md`pada bagian `AL-01: Normalisasi Kode`

## Kendala

Saya sempat bingung apakah perlu menambahkan normalisasi huruf besar dan trim. Karena awalnya saya belum mengerti apa dampak serta fungsinya pada data jika menambahkan atau tidaknya kedua normalisasi tersebut

## Penggunaan AI

- Alat yang dipakai: Claude
- Bagian yang dibantu AI: field, konstruktor
- Prompt utama:
    - Jelaskan garis besar dari tugas ini (minggu-02.md)
    - Jelaskan fungsi trim()
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Saya menolak normalisasi huruf besar pada kode karena tidak ada aturan yang mewajibkannya, saya hanya memakai `trim()`.
- Satu hal yang saya pahami lebih baik sekarang: Penerapan getter, setter, dan enkapsulasi terasa jauh lebih masuk akal ketika dipraktikkan langsung untuk menjaga integritas data pada class Aset, daripada hanya mempelajari teorinya.