package com.android.greenmarket.repository;

import com.android.greenmarket.domain.DanhMuc;
import com.android.greenmarket.domain.NongSan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NongSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NongSanRepository extends JpaRepository<NongSan, Long> {
    List<NongSan> findByDanhmuc(DanhMuc danhmuc);
}
