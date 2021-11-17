package com.android.greenmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnhNongSan.
 */
@Entity
@Table(name = "anh_nong_san")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnhNongSan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anhNongSans", "danhGias", "chiTietHoaDons", "yeuThiches", "danhmuc", "nhacc" }, allowSetters = true)
    private NongSan anhnongsan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnhNongSan id(Long id) {
        this.id = id;
        return this;
    }

    public String getTen() {
        return this.ten;
    }

    public AnhNongSan ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public NongSan getAnhnongsan() {
        return this.anhnongsan;
    }

    public AnhNongSan anhnongsan(NongSan nongSan) {
        this.setAnhnongsan(nongSan);
        return this;
    }

    public void setAnhnongsan(NongSan nongSan) {
        this.anhnongsan = nongSan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnhNongSan)) {
            return false;
        }
        return id != null && id.equals(((AnhNongSan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnhNongSan{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
