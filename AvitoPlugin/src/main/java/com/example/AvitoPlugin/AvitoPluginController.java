package com.example.AvitoPlugin;

import com.example.AvitoPlugin.model.dao.AdvertisementDao;
import com.example.AvitoPlugin.model.dto.AdvertisementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("avito")
public class AvitoPluginController {
    private final Long avitoId = 1L;

    @Autowired
    private AvitoHttpClientService httpClientService;

    @Autowired
    private AdvertisementDbService advertisementDbService;

    @GetMapping("/advertisement/{number}")
    AdvertisementDao getAdvertisementByNumber(@PathVariable String number) {
        return advertisementDbService.getAdvertisement(avitoId, number)
                .orElseGet(() -> {
                    AdvertisementDto advertisement = httpClientService.getAdvertisement(number);
                    return advertisementDbService.insertNewAdvertisement(advertisement.convertToDao());
                });
    }
}
