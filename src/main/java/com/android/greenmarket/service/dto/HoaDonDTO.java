package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.HoaDon} entity.
 */
public class HoaDonDTO implements Serializable {

    private Long id;

    @NotNull
    private Double tongthanhtoan;

    @NotNull
    private Double chiphivanchuyen;

    @NotNull
    private Instant ngaythanhtoan;

    @NotNull
    private Instant ngaytao;

    private String trangthai;

    private PhuongThucTTDTO phuongthucTT;

    private HinhThucGiaoHangDTO phuongthucGH;

    private KhachHangDTO khachhang;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTongthanhtoan() {
        return tongthanhtoan;
    }

    public void setTongthanhtoan(Double tongthanhtoan) {
        this.tongthanhtoan = tongthanhtoan;
    }

    public Double getChiphivanchuyen() {
        return chiphivanchuyen;
    }

    public void setChiphivanchuyen(Double chiphivanchuyen) {
        this.chiphivanchuyen = chiphivanchuyen;
    }

    public Instant getNgaythanhtoan() {
        return ngaythanhtoan;
    }

    public void setNgaythanhtoan(Instant ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public Instant getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Instant ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public PhuongThucTTDTO getPhuongthucTT() {
        return phuongthucTT;
    }

    public void setPhuongthucTT(PhuongThucTTDTO phuongthucTT) {
        this.phuongthucTT = phuongthucTT;
    }

    public HinhThucGiaoHangDTO getPhuongthucGH() {
        return phuongthucGH;
    }

    public void setPhuongthucGH(HinhThucGiaoHangDTO phuongthucGH) {
        this.phuongthucGH = phuongthucGH;
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
        if (!(o instanceof HoaDonDTO)) {
            return false;
        }

        HoaDonDTO hoaDonDTO = (HoaDonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hoaDonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoaDonDTO{" +
            "id=" + getId() +
            ", tongthanhtoan=" + getTongthanhtoan() +
            ", chiphivanchuyen=" + getChiphivanchuyen() +
            ", ngaythanhtoan='" + getNgaythanhtoan() + "'" +
            ", ngaytao='" + getNgaytao() + "'" +
            ", trangthai='" + getTrangthai() + "'" +
            ", phuongthucTT=" + getPhuongthucTT() +
            ", phuongthucGH=" + getPhuongthucGH() +
            ", khachhang=" + getKhachhang() +
            "}";
    }
}
