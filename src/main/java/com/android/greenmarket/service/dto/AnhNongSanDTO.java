package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.AnhNongSan} entity.
 */
public class AnhNongSanDTO implements Serializable {

    private Long id;

    @NotNull
    private String ten;

    private NongSanDTO anhnongsan;

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

    public NongSanDTO getAnhnongsan() {
        return anhnongsan;
    }

    public void setAnhnongsan(NongSanDTO anhnongsan) {
        this.anhnongsan = anhnongsan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnhNongSanDTO)) {
            return false;
        }

        AnhNongSanDTO anhNongSanDTO = (AnhNongSanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anhNongSanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnhNongSanDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", anhnongsan=" + getAnhnongsan() +
            "}";
    }
}
