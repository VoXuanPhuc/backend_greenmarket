package com.android.greenmarket.repository;

import com.android.greenmarket.domain.YeuThich;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the YeuThich entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YeuThichRepository extends JpaRepository<YeuThich, Long> {}
