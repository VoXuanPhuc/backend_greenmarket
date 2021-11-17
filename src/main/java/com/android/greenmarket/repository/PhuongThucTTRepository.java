package com.android.greenmarket.repository;

import com.android.greenmarket.domain.PhuongThucTT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PhuongThucTT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhuongThucTTRepository extends JpaRepository<PhuongThucTT, Long> {}
