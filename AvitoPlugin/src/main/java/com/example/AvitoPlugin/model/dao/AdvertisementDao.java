package com.example.AvitoPlugin.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advertisement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdvertisementDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_id")
    private Long platformId;
    private String number;
    private String title;
    private String description;
    private String url;
    private Integer fraud;
}
