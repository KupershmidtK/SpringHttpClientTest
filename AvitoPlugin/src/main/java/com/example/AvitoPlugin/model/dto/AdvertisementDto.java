package com.example.AvitoPlugin.model.dto;

import com.example.AvitoPlugin.model.dao.AdvertisementDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdvertisementDto {
    private String number;
    private String title;
    private String description;
    private String url;
    private Integer fraud;

    public AdvertisementDao convertToDao() {
        return new AdvertisementDao(null, 1L, number, title, description, url, fraud);
    }
}

