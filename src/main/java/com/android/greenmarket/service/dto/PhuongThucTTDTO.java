package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.PhuongThucTT} entity.
 */
public class PhuongThucTTDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenPTTT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhuongThucTTDTO)) {
            return false;
        }

        PhuongThucTTDTO phuongThucTTDTO = (PhuongThucTTDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phuongThucTTDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhuongThucTTDTO{" +
            "id=" + getId() +
            ", tenPTTT='" + getTenPTTT() + "'" +
            "}";
    }
}
