package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
