package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.DanhGia} entity.
 */
public class DanhGiaDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer sao;

    @NotNull
    private String chitiet;

    @NotNull
    private String image;

    @NotNull
    private Instant ngaydanhgia;

    private NongSanDTO nongsan;

    private KhachHangDTO khachhang;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSao() {
        return sao;
    }

    public void setSao(Integer sao) {
        this.sao = sao;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Instant getNgaydanhgia() {
        return ngaydanhgia;
    }

    public void setNgaydanhgia(Instant ngaydanhgia) {
        this.ngaydanhgia = ngaydanhgia;
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
        if (!(o instanceof DanhGiaDTO)) {
            return false;
        }

        DanhGiaDTO danhGiaDTO = (DanhGiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, danhGiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DanhGiaDTO{" +
            "id=" + getId() +
            ", sao=" + getSao() +
            ", chitiet='" + getChitiet() + "'" +
            ", image='" + getImage() + "'" +
            ", ngaydanhgia='" + getNgaydanhgia() + "'" +
            ", nongsan=" + getNongsan() +
            ", khachhang=" + getKhachhang() +
            "}";
    }
}
