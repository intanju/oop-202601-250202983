# Sequence Diagram: Pengajuan Peminjaman

Alur ini mengikuti kontrak method `JadwalPeminjaman.ajukan(Peminjaman)` dan aturan di `docs/aturan-bisnis.md`: cek bentrok pada aset yang sama, cek kuota laptop, lalu tambahkan peminjaman. Skenario sukses dan bentrok dipisahkan dengan fragmen `alt`.

Versi gambar: [`sequence-pengajuan.svg`](sequence-pengajuan.svg).

```mermaid
sequenceDiagram
    autonumber
    actor U as Peminjam
    participant P as Peminjaman
    participant J as JadwalPeminjaman
    participant A as Aset

    U->>P: new Peminjaman(id, peminjam, aset, mulai, selesai)
    Note over P: selesai harus setelah mulai<br/>status awal DIAJUKAN
    U->>J: ajukan(baru)
    J->>J: bentrok(baru)

    alt bentrok == true (skenario bentrok)
        J-->>U: throw PeminjamanBentrokException
        Note over J: peminjaman tidak ditambahkan
    else bentrok == false (skenario sukses)
        opt aset.getJenis() == LAPTOP
            J->>A: getJenis()
            A-->>J: LAPTOP
            alt kuota laptop sudah habis
                J-->>U: throw KuotaHabisException
                Note over J: peminjaman tidak ditambahkan
            end
        end
        J->>J: tambah(baru)
        J-->>U: berhasil, status DIAJUKAN
    end
```