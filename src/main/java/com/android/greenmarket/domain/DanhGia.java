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
 * A DanhGia.
 */
@Entity
@Table(name = "danh_gia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DanhGia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sao", nullable = false)
    private Integer sao;

    @NotNull
    @Column(name = "chitiet", nullable = false)
    private String chitiet;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Column(name = "ngaydanhgia", nullable = false)
    private Instant ngaydanhgia;

    @OneToMany(mappedBy = "danhgia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "danhgia" }, allowSetters = true)
    private Set<AnhDanhGia> anhDanhGias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "anhNongSans", "danhGias", "chiTietHoaDons", "yeuThiches", "danhmuc", "nhacc" }, allowSetters = true)
    private NongSan nongsan;

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

    public DanhGia id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSao() {
        return this.sao;
    }

    public DanhGia sao(Integer sao) {
        this.sao = sao;
        return this;
    }

    public void setSao(Integer sao) {
        this.sao = sao;
    }

    public String getChitiet() {
        return this.chitiet;
    }

    public DanhGia chitiet(String chitiet) {
        this.chitiet = chitiet;
        return this;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getImage() {
        return this.image;
    }

    public DanhGia image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Instant getNgaydanhgia() {
        return this.ngaydanhgia;
    }

    public DanhGia ngaydanhgia(Instant ngaydanhgia) {
        this.ngaydanhgia = ngaydanhgia;
        return this;
    }

    public void setNgaydanhgia(Instant ngaydanhgia) {
        this.ngaydanhgia = ngaydanhgia;
    }

    public Set<AnhDanhGia> getAnhDanhGias() {
        return this.anhDanhGias;
    }

    public DanhGia anhDanhGias(Set<AnhDanhGia> anhDanhGias) {
        this.setAnhDanhGias(anhDanhGias);
        return this;
    }

    public DanhGia addAnhDanhGia(AnhDanhGia anhDanhGia) {
        this.anhDanhGias.add(anhDanhGia);
        anhDanhGia.setDanhgia(this);
        return this;
    }

    public DanhGia removeAnhDanhGia(AnhDanhGia anhDanhGia) {
        this.anhDanhGias.remove(anhDanhGia);
        anhDanhGia.setDanhgia(null);
        return this;
    }

    public void setAnhDanhGias(Set<AnhDanhGia> anhDanhGias) {
        if (this.anhDanhGias != null) {
            this.anhDanhGias.forEach(i -> i.setDanhgia(null));
        }
        if (anhDanhGias != null) {
            anhDanhGias.forEach(i -> i.setDanhgia(this));
        }
        this.anhDanhGias = anhDanhGias;
    }

    public NongSan getNongsan() {
        return this.nongsan;
    }

    public DanhGia nongsan(NongSan nongSan) {
        this.setNongsan(nongSan);
        return this;
    }

    public void setNongsan(NongSan nongSan) {
        this.nongsan = nongSan;
    }

    public KhachHang getKhachhang() {
        return this.khachhang;
    }

    public DanhGia khachhang(KhachHang khachHang) {
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
        if (!(o instanceof DanhGia)) {
            return false;
        }
        return id != null && id.equals(((DanhGia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DanhGia{" +
            "id=" + getId() +
            ", sao=" + getSao() +
            ", chitiet='" + getChitiet() + "'" +
            ", image='" + getImage() + "'" +
            ", ngaydanhgia='" + getNgaydanhgia() + "'" +
            "}";
    }
}
