package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.HinhThucGiaoHang} entity.
 */
public class HinhThucGiaoHangDTO implements Serializable {

    private Long id;

    @NotNull
    private String mota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HinhThucGiaoHangDTO)) {
            return false;
        }

        HinhThucGiaoHangDTO hinhThucGiaoHangDTO = (HinhThucGiaoHangDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hinhThucGiaoHangDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HinhThucGiaoHangDTO{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            "}";
    }
}
