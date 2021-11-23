package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HuyenQuan.
 */
@Entity
@Table(name = "huyen_quan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HuyenQuan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "huyen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "khachHangs", "nhaCungCaps", "huyen" }, allowSetters = true)
    private Set<XaPhuong> xaPhuongs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "huyenQuans" }, allowSetters = true)
    private TinhTP tinh;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HuyenQuan id(Long id) {
        this.id = id;
        return this;
    }

    public String getTen() {
        return this.ten;
    }

    public HuyenQuan ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Set<XaPhuong> getXaPhuongs() {
        return this.xaPhuongs;
    }

    public HuyenQuan xaPhuongs(Set<XaPhuong> xaPhuongs) {
        this.setXaPhuongs(xaPhuongs);
        return this;
    }

    public HuyenQuan addXaPhuong(XaPhuong xaPhuong) {
        this.xaPhuongs.add(xaPhuong);
        xaPhuong.setHuyen(this);
        return this;
    }

    public HuyenQuan removeXaPhuong(XaPhuong xaPhuong) {
        this.xaPhuongs.remove(xaPhuong);
        xaPhuong.setHuyen(null);
        return this;
    }

    public void setXaPhuongs(Set<XaPhuong> xaPhuongs) {
        if (this.xaPhuongs != null) {
            this.xaPhuongs.forEach(i -> i.setHuyen(null));
        }
        if (xaPhuongs != null) {
            xaPhuongs.forEach(i -> i.setHuyen(this));
        }
        this.xaPhuongs = xaPhuongs;
    }

    public TinhTP getTinh() {
        return this.tinh;
    }

    public HuyenQuan tinh(TinhTP tinhTP) {
        this.setTinh(tinhTP);
        return this;
    }

    public void setTinh(TinhTP tinhTP) {
        this.tinh = tinhTP;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HuyenQuan)) {
            return false;
        }
        return id != null && id.equals(((HuyenQuan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HuyenQuan{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
