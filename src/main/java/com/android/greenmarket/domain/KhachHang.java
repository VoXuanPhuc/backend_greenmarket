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
 * A KhachHang.
 */
@Entity
@Table(name = "khach_hang")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ho_ten_kh")
    private String hoTenKH;

    @Column(name = "ten_dang_nhap", unique = true)
    private String tenDangNhap;

    @NotNull
    @Column(name = "matkhau", nullable = false)
    private String matkhau;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "sdt", nullable = false, unique = true)
    private String sdt;

    @NotNull
    @Column(name = "ngay_sinh", nullable = false)
    private Instant ngaySinh;

    @Column(name = "gioitinh")
    private String gioitinh;

    @Column(name = "chitietdiachi")
    private String chitietdiachi;

    @OneToMany(mappedBy = "khachhang")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nongsan", "khachhang" }, allowSetters = true)
    private Set<YeuThich> yeuThiches = new HashSet<>();

    @OneToMany(mappedBy = "khachhang")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anhDanhGias", "nongsan", "khachhang" }, allowSetters = true)
    private Set<DanhGia> danhGias = new HashSet<>();

    @OneToMany(mappedBy = "khachhang")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietHoaDons", "phuongthucTT", "phuongthucGH", "khachhang" }, allowSetters = true)
    private Set<HoaDon> hoaDons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "khachHangs", "nhaCungCaps", "huyen" }, allowSetters = true)
    private XaPhuong xa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KhachHang id(Long id) {
        this.id = id;
        return this;
    }

    public String getHoTenKH() {
        return this.hoTenKH;
    }

    public KhachHang hoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
        return this;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public String getTenDangNhap() {
        return this.tenDangNhap;
    }

    public KhachHang tenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        return this;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatkhau() {
        return this.matkhau;
    }

    public KhachHang matkhau(String matkhau) {
        this.matkhau = matkhau;
        return this;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return this.email;
    }

    public KhachHang email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return this.sdt;
    }

    public KhachHang sdt(String sdt) {
        this.sdt = sdt;
        return this;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Instant getNgaySinh() {
        return this.ngaySinh;
    }

    public KhachHang ngaySinh(Instant ngaySinh) {
        this.ngaySinh = ngaySinh;
        return this;
    }

    public void setNgaySinh(Instant ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioitinh() {
        return this.gioitinh;
    }

    public KhachHang gioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
        return this;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getChitietdiachi() {
        return this.chitietdiachi;
    }

    public KhachHang chitietdiachi(String chitietdiachi) {
        this.chitietdiachi = chitietdiachi;
        return this;
    }

    public void setChitietdiachi(String chitietdiachi) {
        this.chitietdiachi = chitietdiachi;
    }

    public Set<YeuThich> getYeuThiches() {
        return this.yeuThiches;
    }

    public KhachHang yeuThiches(Set<YeuThich> yeuThiches) {
        this.setYeuThiches(yeuThiches);
        return this;
    }

    public KhachHang addYeuThich(YeuThich yeuThich) {
        this.yeuThiches.add(yeuThich);
        yeuThich.setKhachhang(this);
        return this;
    }

    public KhachHang removeYeuThich(YeuThich yeuThich) {
        this.yeuThiches.remove(yeuThich);
        yeuThich.setKhachhang(null);
        return this;
    }

    public void setYeuThiches(Set<YeuThich> yeuThiches) {
        if (this.yeuThiches != null) {
            this.yeuThiches.forEach(i -> i.setKhachhang(null));
        }
        if (yeuThiches != null) {
            yeuThiches.forEach(i -> i.setKhachhang(this));
        }
        this.yeuThiches = yeuThiches;
    }

    public Set<DanhGia> getDanhGias() {
        return this.danhGias;
    }

    public KhachHang danhGias(Set<DanhGia> danhGias) {
        this.setDanhGias(danhGias);
        return this;
    }

    public KhachHang addDanhGia(DanhGia danhGia) {
        this.danhGias.add(danhGia);
        danhGia.setKhachhang(this);
        return this;
    }

    public KhachHang removeDanhGia(DanhGia danhGia) {
        this.danhGias.remove(danhGia);
        danhGia.setKhachhang(null);
        return this;
    }

    public void setDanhGias(Set<DanhGia> danhGias) {
        if (this.danhGias != null) {
            this.danhGias.forEach(i -> i.setKhachhang(null));
        }
        if (danhGias != null) {
            danhGias.forEach(i -> i.setKhachhang(this));
        }
        this.danhGias = danhGias;
    }

    public Set<HoaDon> getHoaDons() {
        return this.hoaDons;
    }

    public KhachHang hoaDons(Set<HoaDon> hoaDons) {
        this.setHoaDons(hoaDons);
        return this;
    }

    public KhachHang addHoaDon(HoaDon hoaDon) {
        this.hoaDons.add(hoaDon);
        hoaDon.setKhachhang(this);
        return this;
    }

    public KhachHang removeHoaDon(HoaDon hoaDon) {
        this.hoaDons.remove(hoaDon);
        hoaDon.setKhachhang(null);
        return this;
    }

    public void setHoaDons(Set<HoaDon> hoaDons) {
        if (this.hoaDons != null) {
            this.hoaDons.forEach(i -> i.setKhachhang(null));
        }
        if (hoaDons != null) {
            hoaDons.forEach(i -> i.setKhachhang(this));
        }
        this.hoaDons = hoaDons;
    }

    public XaPhuong getXa() {
        return this.xa;
    }

    public KhachHang xa(XaPhuong xaPhuong) {
        this.setXa(xaPhuong);
        return this;
    }

    public void setXa(XaPhuong xaPhuong) {
        this.xa = xaPhuong;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KhachHang)) {
            return false;
        }
        return id != null && id.equals(((KhachHang) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhachHang{" +
            "id=" + getId() +
            ", hoTenKH='" + getHoTenKH() + "'" +
            ", tenDangNhap='" + getTenDangNhap() + "'" +
            ", matkhau='" + getMatkhau() + "'" +
            ", email='" + getEmail() + "'" +
            ", sdt='" + getSdt() + "'" +
            ", ngaySinh='" + getNgaySinh() + "'" +
            ", gioitinh='" + getGioitinh() + "'" +
            ", chitietdiachi='" + getChitietdiachi() + "'" +
            "}";
    }
}
