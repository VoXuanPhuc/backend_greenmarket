package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.android.greenmarket.domain.DanhMuc} entity.
 */
public class DanhMucDTO implements Serializable {

    private Long id;

    private String tenDM;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DanhMucDTO)) {
            return false;
        }

        DanhMucDTO danhMucDTO = (DanhMucDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, danhMucDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DanhMucDTO{" +
            "id=" + getId() +
            ", tenDM='" + getTenDM() + "'" +
            "}";
    }
}
