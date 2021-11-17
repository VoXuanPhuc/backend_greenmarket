package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.android.greenmarket.domain.YeuThich} entity.
 */
public class YeuThichDTO implements Serializable {

    private Long id;

    private NongSanDTO nongsan;

    private KhachHangDTO khachhang;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NongSanDTO getNongsan() {
        return nongsan;
    }

    public void setNongsan(NongSanDTO nongsan) {
        this.nongsan = nongsan;
    }

    public KhachHangDTO getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(KhachHangDTO khachhang) {
        this.khachhang = khachhang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YeuThichDTO)) {
            return false;
        }

        YeuThichDTO yeuThichDTO = (YeuThichDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, yeuThichDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "YeuThichDTO{" +
            "id=" + getId() +
            ", nongsan=" + getNongsan() +
            ", khachhang=" + getKhachhang() +
            "}";
    }
}
