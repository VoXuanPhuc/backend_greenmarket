package com.android.greenmarket.repository;

import com.android.greenmarket.domain.TinhTP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TinhTP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TinhTPRepository extends JpaRepository<TinhTP, Long> {}
