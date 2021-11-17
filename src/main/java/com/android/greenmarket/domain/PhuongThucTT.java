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
 * A PhuongThucTT.
 */
@Entity
@Table(name = "phuong_thuc_tt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PhuongThucTT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ten_pttt", nullable = false)
    private String tenPTTT;

    @OneToMany(mappedBy = "phuongthucTT")
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

    public PhuongThucTT id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenPTTT() {
        return this.tenPTTT;
    }

    public PhuongThucTT tenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
        return this;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public Set<HoaDon> getHoaDons() {
        return this.hoaDons;
    }

    public PhuongThucTT hoaDons(Set<HoaDon> hoaDons) {
        this.setHoaDons(hoaDons);
        return this;
    }

    public PhuongThucTT addHoaDon(HoaDon hoaDon) {
        this.hoaDons.add(hoaDon);
        hoaDon.setPhuongthucTT(this);
        return this;
    }

    public PhuongThucTT removeHoaDon(HoaDon hoaDon) {
        this.hoaDons.remove(hoaDon);
        hoaDon.setPhuongthucTT(null);
        return this;
    }

    public void setHoaDons(Set<HoaDon> hoaDons) {
        if (this.hoaDons != null) {
            this.hoaDons.forEach(i -> i.setPhuongthucTT(null));
        }
        if (hoaDons != null) {
            hoaDons.forEach(i -> i.setPhuongthucTT(this));
        }
        this.hoaDons = hoaDons;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhuongThucTT)) {
            return false;
        }
        return id != null && id.equals(((PhuongThucTT) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhuongThucTT{" +
            "id=" + getId() +
            ", tenPTTT='" + getTenPTTT() + "'" +
            "}";
    }
}
