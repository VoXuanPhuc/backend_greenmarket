package com.android.greenmarket.repository;

import com.android.greenmarket.domain.ChiTietHoaDon;
import com.android.greenmarket.domain.HoaDon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChiTietHoaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Long> {
    List<ChiTietHoaDon> findByHoadon(HoaDon hoadon);
}
