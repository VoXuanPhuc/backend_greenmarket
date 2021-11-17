package com.android.greenmarket.repository;

import com.android.greenmarket.domain.DanhGia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DanhGia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {}
