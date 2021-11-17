package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.NongSan} entity.
 */
public class NongSanDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenNS;

    @NotNull
    private Double gia;

    @NotNull
    private Integer soluongNhap;

    @NotNull
    private Integer soluongCon;

    @NotNull
    private Instant noiSanXuat;

    @NotNull
    private Instant moTaNS;

    private DanhMucDTO danhmuc;

    private NhaCungCapDTO nhacc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNS() {
        return tenNS;
    }

    public void setTenNS(String tenNS) {
        this.tenNS = tenNS;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoluongNhap() {
        return soluongNhap;
    }

    public void setSoluongNhap(Integer soluongNhap) {
        this.soluongNhap = soluongNhap;
    }

    public Integer getSoluongCon() {
        return soluongCon;
    }

    public void setSoluongCon(Integer soluongCon) {
        this.soluongCon = soluongCon;
    }

    public Instant getNoiSanXuat() {
        return noiSanXuat;
    }

    public void setNoiSanXuat(Instant noiSanXuat) {
        this.noiSanXuat = noiSanXuat;
    }

    public Instant getMoTaNS() {
        return moTaNS;
    }

    public void setMoTaNS(Instant moTaNS) {
        this.moTaNS = moTaNS;
    }

    public DanhMucDTO getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(DanhMucDTO danhmuc) {
        this.danhmuc = danhmuc;
    }

    public NhaCungCapDTO getNhacc() {
        return nhacc;
    }

    public void setNhacc(NhaCungCapDTO nhacc) {
        this.nhacc = nhacc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NongSanDTO)) {
            return false;
        }

        NongSanDTO nongSanDTO = (NongSanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nongSanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NongSanDTO{" +
            "id=" + getId() +
            ", tenNS='" + getTenNS() + "'" +
            ", gia=" + getGia() +
            ", soluongNhap=" + getSoluongNhap() +
            ", soluongCon=" + getSoluongCon() +
            ", noiSanXuat='" + getNoiSanXuat() + "'" +
            ", moTaNS='" + getMoTaNS() + "'" +
            ", danhmuc=" + getDanhmuc() +
            ", nhacc=" + getNhacc() +
            "}";
    }
}
