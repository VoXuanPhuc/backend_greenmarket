package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NongSan.
 */
@Entity
@Table(name = "nong_san")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NongSan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ten_ns", nullable = false)
    private String tenNS;

    @NotNull
    @Column(name = "gia", nullable = false)
    private Double gia;

    @NotNull
    @Column(name = "soluong_nhap", nullable = false)
    private Integer soluongNhap;

    @NotNull
    @Column(name = "soluong_con", nullable = false)
    private Integer soluongCon;

    @NotNull
    @Column(name = "noi_san_xuat", nullable = false)
    private String noiSanXuat;

    @NotNull
    @Column(name = "mo_ta_ns", nullable = false)
    private String moTaNS;

    @OneToMany(mappedBy = "anhnongsan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anhnongsan" }, allowSetters = true)
    private Set<AnhNongSan> anhNongSans = new HashSet<>();

    @OneToMany(mappedBy = "nongsan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anhDanhGias", "nongsan", "khachhang" }, allowSetters = true)
    private Set<DanhGia> danhGias = new HashSet<>();

    @OneToMany(mappedBy = "nongsan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nongsan", "hoadon" }, allowSetters = true)
    private Set<ChiTietHoaDon> chiTietHoaDons = new HashSet<>();

    @OneToMany(mappedBy = "nongsan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nongsan", "khachhang" }, allowSetters = true)
    private Set<YeuThich> yeuThiches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "nongSans" }, allowSetters = true)
    private DanhMuc danhmuc;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diaChi", "nongSans" }, allowSetters = true)
    private NhaCungCap nhacc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NongSan id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenNS() {
        return this.tenNS;
    }

    public NongSan tenNS(String tenNS) {
        this.tenNS = tenNS;
        return this;
    }

    public void setTenNS(String tenNS) {
        this.tenNS = tenNS;
    }

    public Double getGia() {
        return this.gia;
    }

    public NongSan gia(Double gia) {
        this.gia = gia;
        return this;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoluongNhap() {
        return this.soluongNhap;
    }

    public NongSan soluongNhap(Integer soluongNhap) {
        this.soluongNhap = soluongNhap;
        return this;
    }

    public void setSoluongNhap(Integer soluongNhap) {
        this.soluongNhap = soluongNhap;
    }

    public Integer getSoluongCon() {
        return this.soluongCon;
    }

    public NongSan soluongCon(Integer soluongCon) {
        this.soluongCon = soluongCon;
        return this;
    }

    public void setSoluongCon(Integer soluongCon) {
        this.soluongCon = soluongCon;
    }

    public String getNoiSanXuat() {
        return this.noiSanXuat;
    }

    public NongSan noiSanXuat(String noiSanXuat) {
        this.noiSanXuat = noiSanXuat;
        return this;
    }

    public void setNoiSanXuat(String noiSanXuat) {
        this.noiSanXuat = noiSanXuat;
    }

    public String getMoTaNS() {
        return this.moTaNS;
    }

    public NongSan moTaNS(String moTaNS) {
        this.moTaNS = moTaNS;
        return this;
    }

    public void setMoTaNS(String moTaNS) {
        this.moTaNS = moTaNS;
    }

    public Set<AnhNongSan> getAnhNongSans() {
        return this.anhNongSans;
    }

    public NongSan anhNongSans(Set<AnhNongSan> anhNongSans) {
        this.setAnhNongSans(anhNongSans);
        return this;
    }

    public NongSan addAnhNongSan(AnhNongSan anhNongSan) {
        this.anhNongSans.add(anhNongSan);
        anhNongSan.setAnhnongsan(this);
        return this;
    }

    public NongSan removeAnhNongSan(AnhNongSan anhNongSan) {
        this.anhNongSans.remove(anhNongSan);
        anhNongSan.setAnhnongsan(null);
        return this;
    }

    public void setAnhNongSans(Set<AnhNongSan> anhNongSans) {
        if (this.anhNongSans != null) {
            this.anhNongSans.forEach(i -> i.setAnhnongsan(null));
        }
        if (anhNongSans != null) {
            anhNongSans.forEach(i -> i.setAnhnongsan(this));
        }
        this.anhNongSans = anhNongSans;
    }

    public Set<DanhGia> getDanhGias() {
        return this.danhGias;
    }

    public NongSan danhGias(Set<DanhGia> danhGias) {
        this.setDanhGias(danhGias);
        return this;
    }

    public NongSan addDanhGia(DanhGia danhGia) {
        this.danhGias.add(danhGia);
        danhGia.setNongsan(this);
        return this;
    }

    public NongSan removeDanhGia(DanhGia danhGia) {
        this.danhGias.remove(danhGia);
        danhGia.setNongsan(null);
        return this;
    }

    public void setDanhGias(Set<DanhGia> danhGias) {
        if (this.danhGias != null) {
            this.danhGias.forEach(i -> i.setNongsan(null));
        }
        if (danhGias != null) {
            danhGias.forEach(i -> i.setNongsan(this));
        }
        this.danhGias = danhGias;
    }

    public Set<ChiTietHoaDon> getChiTietHoaDons() {
        return this.chiTietHoaDons;
    }

    public NongSan chiTietHoaDons(Set<ChiTietHoaDon> chiTietHoaDons) {
        this.setChiTietHoaDons(chiTietHoaDons);
        return this;
    }

    public NongSan addChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDons.add(chiTietHoaDon);
        chiTietHoaDon.setNongsan(this);
        return this;
    }

    public NongSan removeChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDons.remove(chiTietHoaDon);
        chiTietHoaDon.setNongsan(null);
        return this;
    }

    public void setChiTietHoaDons(Set<ChiTietHoaDon> chiTietHoaDons) {
        if (this.chiTietHoaDons != null) {
            this.chiTietHoaDons.forEach(i -> i.setNongsan(null));
        }
        if (chiTietHoaDons != null) {
            chiTietHoaDons.forEach(i -> i.setNongsan(this));
        }
        this.chiTietHoaDons = chiTietHoaDons;
    }

    public Set<YeuThich> getYeuThiches() {
        return this.yeuThiches;
    }

    public NongSan yeuThiches(Set<YeuThich> yeuThiches) {
        this.setYeuThiches(yeuThiches);
        return this;
    }

    public NongSan addYeuThich(YeuThich yeuThich) {
        this.yeuThiches.add(yeuThich);
        yeuThich.setNongsan(this);
        return this;
    }

    public NongSan removeYeuThich(YeuThich yeuThich) {
        this.yeuThiches.remove(yeuThich);
        yeuThich.setNongsan(null);
        return this;
    }

    public void setYeuThiches(Set<YeuThich> yeuThiches) {
        if (this.yeuThiches != null) {
            this.yeuThiches.forEach(i -> i.setNongsan(null));
        }
        if (yeuThiches != null) {
            yeuThiches.forEach(i -> i.setNongsan(this));
        }
        this.yeuThiches = yeuThiches;
    }

    public DanhMuc getDanhmuc() {
        return this.danhmuc;
    }

    public NongSan danhmuc(DanhMuc danhMuc) {
        this.setDanhmuc(danhMuc);
        return this;
    }

    public void setDanhmuc(DanhMuc danhMuc) {
        this.danhmuc = danhMuc;
    }

    public NhaCungCap getNhacc() {
        return this.nhacc;
    }

    public NongSan nhacc(NhaCungCap nhaCungCap) {
        this.setNhacc(nhaCungCap);
        return this;
    }

    public void setNhacc(NhaCungCap nhaCungCap) {
        this.nhacc = nhaCungCap;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NongSan)) {
            return false;
        }
        return id != null && id.equals(((NongSan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NongSan{" +
            "id=" + getId() +
            ", tenNS='" + getTenNS() + "'" +
            ", gia=" + getGia() +
            ", soluongNhap=" + getSoluongNhap() +
            ", soluongCon=" + getSoluongCon() +
            ", noiSanXuat='" + getNoiSanXuat() + "'" +
            ", moTaNS='" + getMoTaNS() + "'" +
            "}";
    }
}
