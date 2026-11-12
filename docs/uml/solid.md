# Prinsip SOLID di Aset-Pinjam

## 1. Open/Closed Principle (OCP): `AturanDenda`, `DendaFlat`, `DendaProgresif`

Cara menghitung denda saya pisahkan ke interface `AturanDenda`. Kalau butuh aturan baru, saya tinggal bikin class baru yang mengimplementasikan interface itu, tanpa mengubah `DendaFlat`, `DendaProgresif`, `Aset`, atau tes lama. Contohnya `DendaProgresif` yang saya tulis di minggu 5: dia menambah perilaku baru, tetapi `DendaFlat` tetap jalan seperti sebelumnya. Jadi kode terbuka untuk ditambah, tapi tertutup untuk diubah.

## 2. Liskov Substitution Principle (LSP): `Aset` dengan `Ruang`, `Laptop`, dan `AlatLab`

Ketiga subclass bisa dipakai di semua tempat yang meminta `Aset` tanpa mengubah perilaku yang diharapkan. `KatalogAset` menyimpan `List<Aset>`, dan `DendaFlat.hitung(Aset, int)` memanggil `aset.hitungDenda(...)` tanpa peduli jenis asetnya. Validasi dasar dari `Aset` tetap berlaku lewat `super(kode, nama)`, dan subclass hanya menambah aturan (misalnya kapasitas harus lebih dari 0), tidak melonggarkan aturan induknya. Tes `semuaJenisBisaDiperlakukanSebagaiAset` di `Minggu03HierarkiTest` membuktikan hal ini.

## 3. Interface Segregation Principle (ISP): `DapatDipinjam` dan `AturanDenda`

Kedua interface ini kecil dan cuma punya satu method. `DapatDipinjam` hanya berisi `bisaDipinjam()`, sedangkan `AturanDenda` hanya berisi `hitung(...)`. Jadi class yang memakainya tidak dipaksa mengisi method yang tidak dibutuhkan. `Aset` cukup mengimplementasikan `bisaDipinjam()`, dan class denda cukup mengimplementasikan `hitung(...)`.
