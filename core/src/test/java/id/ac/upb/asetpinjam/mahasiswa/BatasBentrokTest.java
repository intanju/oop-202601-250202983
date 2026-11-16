package id.ac.upb.asetpinjam.mahasiswa;

import static id.ac.upb.asetpinjam.TestSupport.jam;
import static id.ac.upb.asetpinjam.TestSupport.peminjam;
import static id.ac.upb.asetpinjam.TestSupport.pinjam;
import static id.ac.upb.asetpinjam.TestSupport.ruang;
import static org.assertj.core.api.Assertions.assertThat;

import id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman;
import id.ac.upb.asetpinjam.model.Peminjaman;
import id.ac.upb.asetpinjam.model.Ruang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * FOLDER INI MILIK ANDA. Tulis tes Anda sendiri di sini (minggu 7 dan seterusnya).
 *
 * <p>Tes dosen sengaja tidak menentukan: apakah peminjaman 09:00-11:00 dan 11:00-13:00 pada aset yang sama
 * bentrok? Putuskan, catat alasannya di docs/asumsi.md, lalu tuliskan tesnya di sini.
 * Hapus @Disabled setelah Anda mengisinya.
 *
 * <p>Syarat minggu 10: folder ini memuat minimal 8 tes (tidak termasuk contoh bawaan ini).
 */
@Tag("week7")
class BatasBentrokTest {

    private JadwalPeminjaman jadwal;
    private Ruang aset;

    @BeforeEach
    void siapkan() {
        jadwal = new JadwalPeminjaman(1);
        aset = ruang("R-1");
    }

    @Test
    void selesaiSamaDenganMulaiTidakBentrok() {
        jadwal.tambah(pinjam("1", peminjam("P1"), aset, 9, 11));
        Peminjaman berikutnya = pinjam("2", peminjam("P2"), aset, 11, 13);

        assertThat(jadwal.bentrok(berikutnya)).isFalse();
    }

    @Test
    void selesaiSamaDenganMulaiTidakBentrokMeskiUrutanTerbalik() {
        jadwal.tambah(pinjam("1", peminjam("P1"), aset, 11, 13));
        Peminjaman sebelumnya = pinjam("2", peminjam("P2"), aset, 9, 11);

        assertThat(jadwal.bentrok(sebelumnya)).isFalse();
    }

    @Test
    void selisihSatuMenitTetapBentrok() {
        jadwal.tambah(pinjam("1", peminjam("P1"), aset, 9, 11));
        Peminjaman berikutnya = new Peminjaman(
                "2", peminjam("P2"), aset, jam(11).minusMinutes(1), jam(13));

        assertThat(jadwal.bentrok(berikutnya)).isTrue();
    }
}