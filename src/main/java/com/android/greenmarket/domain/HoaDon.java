package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HoaDon.
 */
@Entity
@Table(name = "hoa_don")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tongthanhtoan", nullable = false)
    private Double tongthanhtoan;

    @NotNull
    @Column(name = "chiphivanchuyen", nullable = false)
    private Double chiphivanchuyen;

    @NotNull
    @Column(name = "ngaythanhtoan", nullable = false)
    private Instant ngaythanhtoan;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Instant ngaytao;

    @Column(name = "trangthai")
    private String trangthai;

    @OneToMany(mappedBy = "hoadon")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nongsan", "hoadon" }, allowSetters = true)
    private Set<ChiTietHoaDon> chiTietHoaDons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "hoaDons" }, allowSetters = true)
    private PhuongThucTT phuongthucTT;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hoaDons" }, allowSetters = true)
    private HinhThucGiaoHang phuongthucGH;

    @ManyToOne
    @JsonIgnoreProperties(value = { "yeuThiches", "danhGias", "hoaDons", "xa" }, allowSetters = true)
    private KhachHang khachhang;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoaDon id(Long id) {
        this.id = id;
        return this;
    }

    public Double getTongthanhtoan() {
        return this.tongthanhtoan;
    }

    public HoaDon tongthanhtoan(Double tongthanhtoan) {
        this.tongthanhtoan = tongthanhtoan;
        return this;
    }

    public void setTongthanhtoan(Double tongthanhtoan) {
        this.tongthanhtoan = tongthanhtoan;
    }

    public Double getChiphivanchuyen() {
        return this.chiphivanchuyen;
    }

    public HoaDon chiphivanchuyen(Double chiphivanchuyen) {
        this.chiphivanchuyen = chiphivanchuyen;
        return this;
    }

    public void setChiphivanchuyen(Double chiphivanchuyen) {
        this.chiphivanchuyen = chiphivanchuyen;
    }

    public Instant getNgaythanhtoan() {
        return this.ngaythanhtoan;
    }

    public HoaDon ngaythanhtoan(Instant ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
        return this;
    }

    public void setNgaythanhtoan(Instant ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public Instant getNgaytao() {
        return this.ngaytao;
    }

    public HoaDon ngaytao(Instant ngaytao) {
        this.ngaytao = ngaytao;
        return this;
    }

    public void setNgaytao(Instant ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getTrangthai() {
        return this.trangthai;
    }

    public HoaDon trangthai(String trangthai) {
        this.trangthai = trangthai;
        return this;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public Set<ChiTietHoaDon> getChiTietHoaDons() {
        return this.chiTietHoaDons;
    }

    public HoaDon chiTietHoaDons(Set<ChiTietHoaDon> chiTietHoaDons) {
        this.setChiTietHoaDons(chiTietHoaDons);
        return this;
    }

    public HoaDon addChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDons.add(chiTietHoaDon);
        chiTietHoaDon.setHoadon(this);
        return this;
    }

    public HoaDon removeChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDons.remove(chiTietHoaDon);
        chiTietHoaDon.setHoadon(null);
        return this;
    }

    public void setChiTietHoaDons(Set<ChiTietHoaDon> chiTietHoaDons) {
        if (this.chiTietHoaDons != null) {
            this.chiTietHoaDons.forEach(i -> i.setHoadon(null));
        }
        if (chiTietHoaDons != null) {
            chiTietHoaDons.forEach(i -> i.setHoadon(this));
        }
        this.chiTietHoaDons = chiTietHoaDons;
    }

    public PhuongThucTT getPhuongthucTT() {
        return this.phuongthucTT;
    }

    public HoaDon phuongthucTT(PhuongThucTT phuongThucTT) {
        this.setPhuongthucTT(phuongThucTT);
        return this;
    }

    public void setPhuongthucTT(PhuongThucTT phuongThucTT) {
        this.phuongthucTT = phuongThucTT;
    }

    public HinhThucGiaoHang getPhuongthucGH() {
        return this.phuongthucGH;
    }

    public HoaDon phuongthucGH(HinhThucGiaoHang hinhThucGiaoHang) {
        this.setPhuongthucGH(hinhThucGiaoHang);
        return this;
    }

    public void setPhuongthucGH(HinhThucGiaoHang hinhThucGiaoHang) {
        this.phuongthucGH = hinhThucGiaoHang;
    }

    public KhachHang getKhachhang() {
        return this.khachhang;
    }

    public HoaDon khachhang(KhachHang khachHang) {
        this.setKhachhang(khachHang);
        return this;
    }

    public void setKhachhang(KhachHang khachHang) {
        this.khachhang = khachHang;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoaDon)) {
            return false;
        }
        return id != null && id.equals(((HoaDon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoaDon{" +
            "id=" + getId() +
            ", tongthanhtoan=" + getTongthanhtoan() +
            ", chiphivanchuyen=" + getChiphivanchuyen() +
            ", ngaythanhtoan='" + getNgaythanhtoan() + "'" +
            ", ngaytao='" + getNgaytao() + "'" +
            ", trangthai='" + getTrangthai() + "'" +
            "}";
    }
}
