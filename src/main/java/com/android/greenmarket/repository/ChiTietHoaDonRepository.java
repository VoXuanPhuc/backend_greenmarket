package com.android.greenmarket.repository;

import com.android.greenmarket.domain.ChiTietHoaDon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChiTietHoaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Long> {}
