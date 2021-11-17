package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.android.greenmarket.domain.XaPhuong} entity.
 */
public class XaPhuongDTO implements Serializable {

    private Long id;

    private String ten;

    private HuyenQuanDTO huyen;

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

    public HuyenQuanDTO getHuyen() {
        return huyen;
    }

    public void setHuyen(HuyenQuanDTO huyen) {
        this.huyen = huyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XaPhuongDTO)) {
            return false;
        }

        XaPhuongDTO xaPhuongDTO = (XaPhuongDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, xaPhuongDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "XaPhuongDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", huyen=" + getHuyen() +
            "}";
    }
}
