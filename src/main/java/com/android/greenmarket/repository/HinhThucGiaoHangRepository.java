package com.android.greenmarket.repository;

import com.android.greenmarket.domain.HinhThucGiaoHang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HinhThucGiaoHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HinhThucGiaoHangRepository extends JpaRepository<HinhThucGiaoHang, Long> {}
