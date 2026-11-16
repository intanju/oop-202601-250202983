package id.ac.upb.asetpinjam.jadwal;

import id.ac.upb.asetpinjam.model.Peminjaman;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Jadwal peminjaman di memori.
 *
 * <p>Minggu 7: bentrok, semuaUrutMulai, perAset, hapus. Minggu 9: ajukan (melempar exception).
 * tambah() DISEDIAKAN dan sengaja TIDAK memeriksa apa pun.
 */
public class JadwalPeminjaman {

    private final List<Peminjaman> daftar = new ArrayList<>();
    private final int kuotaLaptop;

    public JadwalPeminjaman(int kuotaLaptop) {
        this.kuotaLaptop = kuotaLaptop;
    }

    protected int kuotaLaptop() {
        return kuotaLaptop;
    }

    protected List<Peminjaman> daftarMutable() {
        return daftar;
    }

    /** Menambah apa adanya tanpa pemeriksaan. */
    public void tambah(Peminjaman p) {
        daftar.add(p);
    }

    /**
     * True jika {@code baru} bentrok dengan peminjaman yang sudah ada untuk aset yang sama.
     *
     * <p>Rentang diperlakukan setengah terbuka: [mulai, selesai). Jadi peminjaman yang selesai tepat
     * saat peminjaman lain mulai tidak dianggap bentrok (lihat docs/asumsi.md A-01).
     */
    public boolean bentrok(Peminjaman baru) {
        for (Peminjaman lama : daftar) {
            boolean asetSama = lama.getAset().getKode().equals(baru.getAset().getKode());
            boolean tumpangTindih = baru.getMulai().isBefore(lama.getSelesai())
                    && lama.getMulai().isBefore(baru.getSelesai());
            if (asetSama && tumpangTindih) {
                return true;
            }
        }
        return false;
    }

    /** Semua peminjaman, diurutkan menaik menurut waktu mulai. */
    public List<Peminjaman> semuaUrutMulai() {
        List<Peminjaman> hasil = new ArrayList<>(daftar);
        hasil.sort(Comparator.comparing(Peminjaman::getMulai));
        return hasil;
    }

    /** Peminjaman dikelompokkan menurut kode aset, dengan urutan kunci sesuai urutan masuk. */
    public Map<String, List<Peminjaman>> perAset() {
        Map<String, List<Peminjaman>> hasil = new LinkedHashMap<>();
        for (Peminjaman p : daftar) {
            hasil.computeIfAbsent(p.getAset().getKode(), kode -> new ArrayList<>()).add(p);
        }
        return hasil;
    }

    /** Hapus peminjaman berdasarkan id. True jika ada yang terhapus. */
    public boolean hapus(String id) {
        return daftar.removeIf(p -> p.getId().equals(id));
    }

    /**
     * TODO minggu 9: periksa lalu tambahkan.
     * Bentrok: PeminjamanBentrokException. Peminjam sudah memegang {@link #kuotaLaptop()} laptop yang
     * aktif (status DISETUJUI atau DIPINJAM) dan mengajukan laptop lagi: KuotaHabisException.
     */
    public void ajukan(Peminjaman baru) {
        throw new UnsupportedOperationException("TODO minggu 9: ajukan");
    }
}
