package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.android.greenmarket.domain.TinhTP} entity.
 */
public class TinhTPDTO implements Serializable {

    private Long id;

    private String ten;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TinhTPDTO)) {
            return false;
        }

        TinhTPDTO tinhTPDTO = (TinhTPDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tinhTPDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TinhTPDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
