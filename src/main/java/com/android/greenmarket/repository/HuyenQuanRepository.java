package com.android.greenmarket.repository;

import com.android.greenmarket.domain.HuyenQuan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HuyenQuan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HuyenQuanRepository extends JpaRepository<HuyenQuan, Long> {}
