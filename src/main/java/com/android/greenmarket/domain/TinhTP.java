package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TinhTP.
 */
@Entity
@Table(name = "tinh_tp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TinhTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "tinh")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "xaPhuongs", "tinh" }, allowSetters = true)
    private Set<HuyenQuan> huyenQuans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TinhTP id(Long id) {
        this.id = id;
        return this;
    }

    public String getTen() {
        return this.ten;
    }

    public TinhTP ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Set<HuyenQuan> getHuyenQuans() {
        return this.huyenQuans;
    }

    public TinhTP huyenQuans(Set<HuyenQuan> huyenQuans) {
        this.setHuyenQuans(huyenQuans);
        return this;
    }

    public TinhTP addHuyenQuan(HuyenQuan huyenQuan) {
        this.huyenQuans.add(huyenQuan);
        huyenQuan.setTinh(this);
        return this;
    }

    public TinhTP removeHuyenQuan(HuyenQuan huyenQuan) {
        this.huyenQuans.remove(huyenQuan);
        huyenQuan.setTinh(null);
        return this;
    }

    public void setHuyenQuans(Set<HuyenQuan> huyenQuans) {
        if (this.huyenQuans != null) {
            this.huyenQuans.forEach(i -> i.setTinh(null));
        }
        if (huyenQuans != null) {
            huyenQuans.forEach(i -> i.setTinh(this));
        }
        this.huyenQuans = huyenQuans;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TinhTP)) {
            return false;
        }
        return id != null && id.equals(((TinhTP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TinhTP{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
