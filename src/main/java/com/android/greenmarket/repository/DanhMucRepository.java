package com.android.greenmarket.repository;

import com.android.greenmarket.domain.DanhMuc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DanhMuc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {}
