package com.android.greenmarket.repository;

import com.android.greenmarket.domain.AnhDanhGia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnhDanhGia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnhDanhGiaRepository extends JpaRepository<AnhDanhGia, Long> {}
