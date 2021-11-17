package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.android.greenmarket.domain.HuyenQuan} entity.
 */
public class HuyenQuanDTO implements Serializable {

    private Long id;

    private String ten;

    private TinhTPDTO tinh;

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

    public TinhTPDTO getTinh() {
        return tinh;
    }

    public void setTinh(TinhTPDTO tinh) {
        this.tinh = tinh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HuyenQuanDTO)) {
            return false;
        }

        HuyenQuanDTO huyenQuanDTO = (HuyenQuanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, huyenQuanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HuyenQuanDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", tinh=" + getTinh() +
            "}";
    }
}
