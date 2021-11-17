package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChiTietHoaDon.
 */
@Entity
@Table(name = "chi_tiet_hoa_don")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChiTietHoaDon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "gia", nullable = false)
    private Double gia;

    @NotNull
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anhNongSans", "danhGias", "chiTietHoaDons", "yeuThiches", "danhmuc", "nhacc" }, allowSetters = true)
    private NongSan nongsan;

    @ManyToOne
    @JsonIgnoreProperties(value = { "chiTietHoaDons", "phuongthucTT", "phuongthucGH", "khachhang" }, allowSetters = true)
    private HoaDon hoadon;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChiTietHoaDon id(Long id) {
        this.id = id;
        return this;
    }

    public Double getGia() {
        return this.gia;
    }

    public ChiTietHoaDon gia(Double gia) {
        this.gia = gia;
        return this;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoluong() {
        return this.soluong;
    }

    public ChiTietHoaDon soluong(Integer soluong) {
        this.soluong = soluong;
        return this;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public NongSan getNongsan() {
        return this.nongsan;
    }

    public ChiTietHoaDon nongsan(NongSan nongSan) {
        this.setNongsan(nongSan);
        return this;
    }

    public void setNongsan(NongSan nongSan) {
        this.nongsan = nongSan;
    }

    public HoaDon getHoadon() {
        return this.hoadon;
    }

    public ChiTietHoaDon hoadon(HoaDon hoaDon) {
        this.setHoadon(hoaDon);
        return this;
    }

    public void setHoadon(HoaDon hoaDon) {
        this.hoadon = hoaDon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiTietHoaDon)) {
            return false;
        }
        return id != null && id.equals(((ChiTietHoaDon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
            "id=" + getId() +
            ", gia=" + getGia() +
            ", soluong=" + getSoluong() +
            "}";
    }
}
