package com.android.greenmarket.repository;

import com.android.greenmarket.domain.NongSan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NongSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NongSanRepository extends JpaRepository<NongSan, Long> {}
