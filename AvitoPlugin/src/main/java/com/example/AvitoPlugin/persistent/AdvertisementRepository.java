package com.example.AvitoPlugin.persistent;

import com.example.AvitoPlugin.model.dao.AdvertisementDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementDao, Long> {
    Optional<AdvertisementDao> getReferenceByPlatformIdAndNumber(Long product_id, String number);
}
