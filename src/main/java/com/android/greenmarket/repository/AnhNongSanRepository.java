package com.android.greenmarket.repository;

import com.android.greenmarket.domain.AnhNongSan;
import com.android.greenmarket.domain.NongSan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnhNongSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnhNongSanRepository extends JpaRepository<AnhNongSan, Long> {
    List<AnhNongSan> findByAnhnongsan(NongSan anhnongsan);
}
