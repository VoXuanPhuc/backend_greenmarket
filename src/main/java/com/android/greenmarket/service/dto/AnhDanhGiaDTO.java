package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.AnhDanhGia} entity.
 */
public class AnhDanhGiaDTO implements Serializable {

    private Long id;

    @NotNull
    private String ten;

    private DanhGiaDTO danhgia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DanhGiaDTO getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(DanhGiaDTO danhgia) {
        this.danhgia = danhgia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnhDanhGiaDTO)) {
            return false;
        }

        AnhDanhGiaDTO anhDanhGiaDTO = (AnhDanhGiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anhDanhGiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnhDanhGiaDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", danhgia=" + getDanhgia() +
            "}";
    }
}
