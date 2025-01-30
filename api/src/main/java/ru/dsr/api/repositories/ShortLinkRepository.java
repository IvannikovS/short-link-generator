package ru.dsr.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.api.entity.ShortLink;


@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, Integer> {
    ShortLink findByShortCode(String shortCode);

//    @Query(value = "insert into link (url, shortCode) values (:url, :shortCode) " +
//            "ON CONFLICT(shortCode) DO UPDATE SET shortCode = excluded.shortCode RETURNING id;",
//            nativeQuery = true)
//    Integer insertOnConflict(@Param("url") String url,
//                                @Param("shortCode") String shortCode);
}
