package com.android.greenmarket.repository;

import com.android.greenmarket.domain.XaPhuong;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the XaPhuong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XaPhuongRepository extends JpaRepository<XaPhuong, Long> {}
