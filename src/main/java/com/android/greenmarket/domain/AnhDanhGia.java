package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnhDanhGia.
 */
@Entity
@Table(name = "anh_danh_gia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnhDanhGia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anhDanhGias", "nongsan", "khachhang" }, allowSetters = true)
    private DanhGia danhgia;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnhDanhGia id(Long id) {
        this.id = id;
        return this;
    }

    public String getTen() {
        return this.ten;
    }

    public AnhDanhGia ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DanhGia getDanhgia() {
        return this.danhgia;
    }

    public AnhDanhGia danhgia(DanhGia danhGia) {
        this.setDanhgia(danhGia);
        return this;
    }

    public void setDanhgia(DanhGia danhGia) {
        this.danhgia = danhGia;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnhDanhGia)) {
            return false;
        }
        return id != null && id.equals(((AnhDanhGia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnhDanhGia{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
