package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.NhaCungCap} entity.
 */
public class NhaCungCapDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenNCC;

    private String chitietdiachi;

    private XaPhuongDTO xa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getChitietdiachi() {
        return chitietdiachi;
    }

    public void setChitietdiachi(String chitietdiachi) {
        this.chitietdiachi = chitietdiachi;
    }

    public XaPhuongDTO getXa() {
        return xa;
    }

    public void setXa(XaPhuongDTO xa) {
        this.xa = xa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaCungCapDTO)) {
            return false;
        }

        NhaCungCapDTO nhaCungCapDTO = (NhaCungCapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nhaCungCapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaCungCapDTO{" +
            "id=" + getId() +
            ", tenNCC='" + getTenNCC() + "'" +
            ", chitietdiachi='" + getChitietdiachi() + "'" +
            ", xa=" + getXa() +
            "}";
    }
}
