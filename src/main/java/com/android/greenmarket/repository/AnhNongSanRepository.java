package com.android.greenmarket.repository;

import com.android.greenmarket.domain.AnhNongSan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnhNongSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnhNongSanRepository extends JpaRepository<AnhNongSan, Long> {}
