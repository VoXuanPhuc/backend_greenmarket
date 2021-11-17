package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DanhMuc.
 */
@Entity
@Table(name = "danh_muc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DanhMuc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_dm")
    private String tenDM;

    @OneToMany(mappedBy = "danhmuc")
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

    public DanhMuc id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenDM() {
        return this.tenDM;
    }

    public DanhMuc tenDM(String tenDM) {
        this.tenDM = tenDM;
        return this;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public Set<NongSan> getNongSans() {
        return this.nongSans;
    }

    public DanhMuc nongSans(Set<NongSan> nongSans) {
        this.setNongSans(nongSans);
        return this;
    }

    public DanhMuc addNongSan(NongSan nongSan) {
        this.nongSans.add(nongSan);
        nongSan.setDanhmuc(this);
        return this;
    }

    public DanhMuc removeNongSan(NongSan nongSan) {
        this.nongSans.remove(nongSan);
        nongSan.setDanhmuc(null);
        return this;
    }

    public void setNongSans(Set<NongSan> nongSans) {
        if (this.nongSans != null) {
            this.nongSans.forEach(i -> i.setDanhmuc(null));
        }
        if (nongSans != null) {
            nongSans.forEach(i -> i.setDanhmuc(this));
        }
        this.nongSans = nongSans;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DanhMuc)) {
            return false;
        }
        return id != null && id.equals(((DanhMuc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DanhMuc{" +
            "id=" + getId() +
            ", tenDM='" + getTenDM() + "'" +
            "}";
    }
}
