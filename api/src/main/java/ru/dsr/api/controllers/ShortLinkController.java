package ru.dsr.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsr.api.config.ShortLinkConfig;
import ru.dsr.api.dto.ShortLinkCreationDto;
import ru.dsr.api.dto.ShortLinkDto;
import ru.dsr.api.entity.ShortLink;
import ru.dsr.api.mapper.ShortLinkMapper;
import ru.dsr.api.services.ShortLinkService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;
    private final ShortLinkConfig shortLinkConfig;

    @Autowired
    public ShortLinkController(ShortLinkService shortLinkService, ShortLinkConfig shortLinkConfig) {
        this.shortLinkService = shortLinkService;
        this.shortLinkConfig = shortLinkConfig;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShortLinkDto>> getShortLinks() {
        return ResponseEntity.ok(shortLinkService.getShortLinks());
    }

    /*
    Было до изменений. Контроллер DTO возвращал
     */

//    @GetMapping("/all/{id}")
//    public ShortLinkDto getShortLink(@PathVariable("id") Integer id) {
//        return ShortLinkMapper.INSTANCE.toDto(shortLinkService.getShortLink(id));
//    }

    /*
    Есть такой вариант. Возвращает Optional
     */
    @GetMapping("/all/{id}")
    public Optional<ShortLink> getShortLink(@PathVariable("id") Integer id) {
        return shortLinkService.getShortLink(id);
    }


    @PostMapping("/shorten")
    public String createShortLink(@RequestBody ShortLinkCreationDto creationDto) {
        ShortLink shortLink = shortLinkService.createShortLink(creationDto);
//        return "Short link created: " + shortLinkConfig.getBaseUrl() + shortLink.getShortCode();
        return shortLinkConfig.getBaseUrl() + shortLink.getShortCode();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        shortLinkService.getShortLink(id);
        shortLinkService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongLink(@PathVariable String shortCode) {
        String longLink = shortLinkService.getLongLinkByShortCode(shortCode);
        if (longLink != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, longLink)
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
