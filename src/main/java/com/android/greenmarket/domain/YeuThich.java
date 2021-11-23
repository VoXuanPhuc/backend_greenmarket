package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A YeuThich.
 */
@Entity
@Table(name = "yeu_thich")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YeuThich implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    public YeuThich id(Long id) {
        this.id = id;
        return this;
    }

    public NongSan getNongsan() {
        return this.nongsan;
    }

    public YeuThich nongsan(NongSan nongSan) {
        this.setNongsan(nongSan);
        return this;
    }

    public void setNongsan(NongSan nongSan) {
        this.nongsan = nongSan;
    }

    public KhachHang getKhachhang() {
        return this.khachhang;
    }

    public YeuThich khachhang(KhachHang khachHang) {
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
        if (!(o instanceof YeuThich)) {
            return false;
        }
        return id != null && id.equals(((YeuThich) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "YeuThich{" +
            "id=" + getId() +
            "}";
    }
}
