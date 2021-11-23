package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A XaPhuong.
 */
@Entity
@Table(name = "xa_phuong")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class XaPhuong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "xa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "yeuThiches", "danhGias", "hoaDons", "xa" }, allowSetters = true)
    private Set<KhachHang> khachHangs = new HashSet<>();

    @OneToMany(mappedBy = "xa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nongSans", "xa" }, allowSetters = true)
    private Set<NhaCungCap> nhaCungCaps = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "xaPhuongs", "tinh" }, allowSetters = true)
    private HuyenQuan huyen;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public XaPhuong id(Long id) {
        this.id = id;
        return this;
    }

    public String getTen() {
        return this.ten;
    }

    public XaPhuong ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Set<KhachHang> getKhachHangs() {
        return this.khachHangs;
    }

    public XaPhuong khachHangs(Set<KhachHang> khachHangs) {
        this.setKhachHangs(khachHangs);
        return this;
    }

    public XaPhuong addKhachHang(KhachHang khachHang) {
        this.khachHangs.add(khachHang);
        khachHang.setXa(this);
        return this;
    }

    public XaPhuong removeKhachHang(KhachHang khachHang) {
        this.khachHangs.remove(khachHang);
        khachHang.setXa(null);
        return this;
    }

    public void setKhachHangs(Set<KhachHang> khachHangs) {
        if (this.khachHangs != null) {
            this.khachHangs.forEach(i -> i.setXa(null));
        }
        if (khachHangs != null) {
            khachHangs.forEach(i -> i.setXa(this));
        }
        this.khachHangs = khachHangs;
    }

    public Set<NhaCungCap> getNhaCungCaps() {
        return this.nhaCungCaps;
    }

    public XaPhuong nhaCungCaps(Set<NhaCungCap> nhaCungCaps) {
        this.setNhaCungCaps(nhaCungCaps);
        return this;
    }

    public XaPhuong addNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCaps.add(nhaCungCap);
        nhaCungCap.setXa(this);
        return this;
    }

    public XaPhuong removeNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCaps.remove(nhaCungCap);
        nhaCungCap.setXa(null);
        return this;
    }

    public void setNhaCungCaps(Set<NhaCungCap> nhaCungCaps) {
        if (this.nhaCungCaps != null) {
            this.nhaCungCaps.forEach(i -> i.setXa(null));
        }
        if (nhaCungCaps != null) {
            nhaCungCaps.forEach(i -> i.setXa(this));
        }
        this.nhaCungCaps = nhaCungCaps;
    }

    public HuyenQuan getHuyen() {
        return this.huyen;
    }

    public XaPhuong huyen(HuyenQuan huyenQuan) {
        this.setHuyen(huyenQuan);
        return this;
    }

    public void setHuyen(HuyenQuan huyenQuan) {
        this.huyen = huyenQuan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XaPhuong)) {
            return false;
        }
        return id != null && id.equals(((XaPhuong) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "XaPhuong{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
