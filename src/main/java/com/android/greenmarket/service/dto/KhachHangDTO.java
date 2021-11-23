package com.android.greenmarket.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.android.greenmarket.domain.KhachHang} entity.
 */
public class KhachHangDTO implements Serializable {

    private Long id;

    private String hoTenKH;

    private String tenDangNhap;

    @NotNull
    private String matkhau;

    @NotNull
    private String email;

    @NotNull
    private String sdt;

    @NotNull
    private Instant ngaySinh;

    private String gioitinh;

    private String chitietdiachi;

    private XaPhuongDTO xa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoTenKH() {
        return hoTenKH;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Instant getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Instant ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
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
        if (!(o instanceof KhachHangDTO)) {
            return false;
        }

        KhachHangDTO khachHangDTO = (KhachHangDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, khachHangDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhachHangDTO{" +
            "id=" + getId() +
            ", hoTenKH='" + getHoTenKH() + "'" +
            ", tenDangNhap='" + getTenDangNhap() + "'" +
            ", matkhau='" + getMatkhau() + "'" +
            ", email='" + getEmail() + "'" +
            ", sdt='" + getSdt() + "'" +
            ", ngaySinh='" + getNgaySinh() + "'" +
            ", gioitinh='" + getGioitinh() + "'" +
            ", chitietdiachi='" + getChitietdiachi() + "'" +
            ", xa=" + getXa() +
            "}";
    }
}
