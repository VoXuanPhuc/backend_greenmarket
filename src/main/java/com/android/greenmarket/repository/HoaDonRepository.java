package com.android.greenmarket.repository;

import com.android.greenmarket.domain.HoaDon;
import com.android.greenmarket.domain.KhachHang;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    List<HoaDon> findByKhachhang(KhachHang khachhang);
}
