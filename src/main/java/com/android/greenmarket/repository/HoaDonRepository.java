package com.android.greenmarket.repository;

import com.android.greenmarket.domain.HoaDon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {}
