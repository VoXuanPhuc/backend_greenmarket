package com.android.greenmarket.repository;

import com.android.greenmarket.domain.NhaCungCap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NhaCungCap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Long> {}
