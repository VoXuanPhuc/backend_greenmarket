package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.ChiTietHoaDon} entity.
 */
public class ChiTietHoaDonDTO implements Serializable {

    private Long id;

    @NotNull
    private Double gia;

    @NotNull
    private Integer soluong;

    private NongSanDTO nongsan;

    private HoaDonDTO hoadon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public NongSanDTO getNongsan() {
        return nongsan;
    }

    public void setNongsan(NongSanDTO nongsan) {
        this.nongsan = nongsan;
    }

    public HoaDonDTO getHoadon() {
        return hoadon;
    }

    public void setHoadon(HoaDonDTO hoadon) {
        this.hoadon = hoadon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiTietHoaDonDTO)) {
            return false;
        }

        ChiTietHoaDonDTO chiTietHoaDonDTO = (ChiTietHoaDonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chiTietHoaDonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietHoaDonDTO{" +
            "id=" + getId() +
            ", gia=" + getGia() +
            ", soluong=" + getSoluong() +
            ", nongsan=" + getNongsan() +
            ", hoadon=" + getHoadon() +
            "}";
    }
}
