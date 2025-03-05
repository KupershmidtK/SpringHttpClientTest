package com.example.AvitoPlugin;

import com.example.AvitoPlugin.model.dao.AdvertisementDao;
import com.example.AvitoPlugin.persistent.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdvertisementDbService {
    @Autowired
    private AdvertisementRepository advRepository;

    public Optional<AdvertisementDao> getAdvertisement(Long platformId, String number) {
        return advRepository.getReferenceByPlatformIdAndNumber(platformId, number);
    }

    @Transactional
    public AdvertisementDao insertNewAdvertisement(AdvertisementDao advertisement) {
        return advRepository.save(advertisement);
    }
}
