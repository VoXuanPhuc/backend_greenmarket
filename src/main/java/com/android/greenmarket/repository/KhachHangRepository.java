package com.android.greenmarket.repository;

import com.android.greenmarket.domain.KhachHang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KhachHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {}
