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
 * A NhaCungCap.
 */
@Entity
@Table(name = "nha_cung_cap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NhaCungCap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ten_ncc", nullable = false)
    private String tenNCC;

    @Column(name = "chitietdiachi")
    private String chitietdiachi;

    @JsonIgnoreProperties(value = { "huyen" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private XaPhuong diaChi;

    @OneToMany(mappedBy = "nhacc")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anhNongSans", "danhGias", "chiTietHoaDons", "yeuThiches", "danhmuc", "nhacc" }, allowSetters = true)
    private Set<NongSan> nongSans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhaCungCap id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenNCC() {
        return this.tenNCC;
    }

    public NhaCungCap tenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
        return this;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getChitietdiachi() {
        return this.chitietdiachi;
    }

    public NhaCungCap chitietdiachi(String chitietdiachi) {
        this.chitietdiachi = chitietdiachi;
        return this;
    }

    public void setChitietdiachi(String chitietdiachi) {
        this.chitietdiachi = chitietdiachi;
    }

    public XaPhuong getDiaChi() {
        return this.diaChi;
    }

    public NhaCungCap diaChi(XaPhuong xaPhuong) {
        this.setDiaChi(xaPhuong);
        return this;
    }

    public void setDiaChi(XaPhuong xaPhuong) {
        this.diaChi = xaPhuong;
    }

    public Set<NongSan> getNongSans() {
        return this.nongSans;
    }

    public NhaCungCap nongSans(Set<NongSan> nongSans) {
        this.setNongSans(nongSans);
        return this;
    }

    public NhaCungCap addNongSan(NongSan nongSan) {
        this.nongSans.add(nongSan);
        nongSan.setNhacc(this);
        return this;
    }

    public NhaCungCap removeNongSan(NongSan nongSan) {
        this.nongSans.remove(nongSan);
        nongSan.setNhacc(null);
        return this;
    }

    public void setNongSans(Set<NongSan> nongSans) {
        if (this.nongSans != null) {
            this.nongSans.forEach(i -> i.setNhacc(null));
        }
        if (nongSans != null) {
            nongSans.forEach(i -> i.setNhacc(this));
        }
        this.nongSans = nongSans;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaCungCap)) {
            return false;
        }
        return id != null && id.equals(((NhaCungCap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaCungCap{" +
            "id=" + getId() +
            ", tenNCC='" + getTenNCC() + "'" +
            ", chitietdiachi='" + getChitietdiachi() + "'" +
            "}";
    }
}
