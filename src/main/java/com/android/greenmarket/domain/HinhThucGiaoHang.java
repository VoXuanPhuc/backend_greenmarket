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
 * A HinhThucGiaoHang.
 */
@Entity
@Table(name = "hinh_thuc_giao_hang")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HinhThucGiaoHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "mota", nullable = false)
    private String mota;

    @OneToMany(mappedBy = "phuongthucGH")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietHoaDons", "phuongthucTT", "phuongthucGH", "khachhang" }, allowSetters = true)
    private Set<HoaDon> hoaDons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HinhThucGiaoHang id(Long id) {
        this.id = id;
        return this;
    }

    public String getMota() {
        return this.mota;
    }

    public HinhThucGiaoHang mota(String mota) {
        this.mota = mota;
        return this;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Set<HoaDon> getHoaDons() {
        return this.hoaDons;
    }

    public HinhThucGiaoHang hoaDons(Set<HoaDon> hoaDons) {
        this.setHoaDons(hoaDons);
        return this;
    }

    public HinhThucGiaoHang addHoaDon(HoaDon hoaDon) {
        this.hoaDons.add(hoaDon);
        hoaDon.setPhuongthucGH(this);
        return this;
    }

    public HinhThucGiaoHang removeHoaDon(HoaDon hoaDon) {
        this.hoaDons.remove(hoaDon);
        hoaDon.setPhuongthucGH(null);
        return this;
    }

    public void setHoaDons(Set<HoaDon> hoaDons) {
        if (this.hoaDons != null) {
            this.hoaDons.forEach(i -> i.setPhuongthucGH(null));
        }
        if (hoaDons != null) {
            hoaDons.forEach(i -> i.setPhuongthucGH(this));
        }
        this.hoaDons = hoaDons;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HinhThucGiaoHang)) {
            return false;
        }
        return id != null && id.equals(((HinhThucGiaoHang) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HinhThucGiaoHang{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            "}";
    }
}
