package com.example.AvitoPlugin;

import com.example.AvitoPlugin.model.dto.AdvertisementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.random.RandomGenerator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AvitoHttpClientService {
    private final RestClient restClient = RestClient.create();
    private final Logger logger = LoggerFactory.getLogger(AvitoHttpClientService.class);

    public AdvertisementDto getAdvertisement(String number) {
        String queryPage = getAvitoPageByNumber(number);
        String advertisementPageUrl = getUrlFromAvitoQueryPage(queryPage);
        String advertisementPageHtml = getAdvertisementByUrl(advertisementPageUrl);

//        try(PrintWriter out = new PrintWriter("./_query.html")) {
//            out.print(queryPage);
//        } catch (Exception ignore) {}
//
//        try(PrintWriter out = new PrintWriter("./_url.txt")) {
//            out.print(advertisementPageUrl);
//        } catch (Exception ignore) {}
//
//        try(PrintWriter out = new PrintWriter("./_advertisement.html")) {
//            out.print(advertisementPageHtml);
//        } catch (Exception ignore) {}

        advertisementPageHtml = advertisementPageHtml.replace('\u2028', ' ');

        String title = getAdvertisementTitle(advertisementPageHtml);
        String description = getAdvertisementDescription(advertisementPageHtml);
        String url = "avito.ru" + advertisementPageUrl;

        return new AdvertisementDto(number, title, description, url, 0);
    }

    private String getAdvertisementTitle(String html) {
        String title = "";

        Pattern titlePattern = Pattern.compile("item-view\\/title-info\">(.*?)<");
        Matcher matcher = titlePattern.matcher(html);
        if(matcher.find()) {
            title = matcher.group(1);
        }
        return title;
    }

    private String getAdvertisementDescription(String html) {
        String description = "";

        Pattern descriptionPattern = Pattern.compile("item-view\\/item-description.*?<p>(.*?)<\\/p>");
        Matcher matcher = descriptionPattern.matcher(html);
        if(matcher.find()) {
            description = matcher.group(1);
        }
        return description;
    }

    private String getAdvertisementByUrl(String url) {

        long timeout =  1000L + RandomGenerator.getDefault().nextLong(2000L);
        try {
            Thread.sleep(timeout);
        } catch (Exception ignore) {}

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("www.avito.ru")
                .path(url)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                .header("Referer", "https://www.avito.ru/")
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            logger.error("{}: {}", response.getStatusCode().value(), response.getStatusText());
                            // TODO Error handler
                        }
                )
                .body(String.class);
    }

    private String getUrlFromAvitoQueryPage(String html) {
        String url = "";

        Pattern urlPattern = Pattern.compile("iva-item-title-CdRXl.*?href=\\\"(.*?)\\?");
        Matcher matcher = urlPattern.matcher(html);

        if(matcher.find()) {
            url = matcher.group(1);
        }
        return url;
    }

    private String getAvitoPageByNumber(String number) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("www.avito.ru")
                .path("/all")
                .queryParam("q", number)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Referer", "https://www.avito.ru/")
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            logger.error("{}: {}", response.getStatusCode().value(), response.getStatusText());
                            // TODO Error handler
                        }
                )
                .body(String.class);
    }
}
