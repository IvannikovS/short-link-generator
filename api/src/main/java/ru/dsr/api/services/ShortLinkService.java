package ru.dsr.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dsr.api.config.ShortLinkConfig;
import ru.dsr.api.dto.ShortLinkCreationDto;
import ru.dsr.api.dto.ShortLinkDto;
import ru.dsr.api.entity.ShortLink;
import ru.dsr.api.mapper.ShortLinkMapper;
import ru.dsr.api.repositories.ShortLinkRepository;
import ru.dsr.api.utils.ShortCodeGenerator;
import ru.dsr.api.utils.exceptions.ShortLinkNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ShortLinkService {

    private final ShortLinkRepository shortLinkRepository;
    private final ShortLinkConfig shortLinkConfig;

    @Autowired
    public ShortLinkService(ShortLinkRepository shortLinkRepository, ShortLinkConfig shortLinkConfig) {
        this.shortLinkRepository = shortLinkRepository;
        this.shortLinkConfig = shortLinkConfig;
    }

    public List<ShortLinkDto> getShortLinks() {
        return shortLinkRepository.findAll().stream()
                .map(ShortLinkMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ShortLink> getShortLink(Integer id) {
        return shortLinkRepository.findById(id);
    }

    @Transactional
    public ShortLink createShortLink(ShortLinkCreationDto creationDto) {
        String shortCode;
        ShortLink existingShortLink;

        do {
            shortCode = ShortCodeGenerator.generateShortCode();
            existingShortLink = shortLinkRepository.findByShortCode(shortCode);
        } while (existingShortLink != null);

        String url = creationDto.getUrl();
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "https://" + url;
        }

        try {
            new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Некорректный URL: " + url, e);
        }

        String generatedLink = shortLinkConfig.getBaseUrl() + shortCode;

        ShortLink newShortLink = new ShortLink(url, shortCode, generatedLink);
        return shortLinkRepository.save(newShortLink);
    }

    public String getLongLinkByShortCode(String shortCode) {
        ShortLink shortLink = shortLinkRepository.findByShortCode(shortCode);
        if (shortLink == null) {
            throw new ShortLinkNotFoundException("Not found short link with shortCode: " + shortCode);
        }
        return shortLink.getUrl();
    }

    @Transactional
    public void delete(Integer id) {
        shortLinkRepository.deleteById(id);
    }


    //    @Transactional
//    public Integer insertOnConflict(ShortLink shortLink) {
//        String shortCode = generateShortCode();
//        ShortLink shortLink = new ShortLink(longLink, shortCode);
//        return shortLinkRepository.insertOnConflict(shortLink.getUrl(), shortLink.getShortCode());
//    }

//    @Transactional
//    public ShortLink createShortLink(String longLink) {
//        String shortCode = ShortCodeGenerator.generateShortCode();
//        ShortLink shortLink = new ShortLink(longLink, shortCode);
//        return shortLinkRepository.save(shortLink);
//    }
}
