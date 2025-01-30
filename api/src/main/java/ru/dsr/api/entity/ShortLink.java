package ru.dsr.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link")
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NonNull
    @Column(name = "url")
    private String url;
    @NonNull
    @Column(name = "shortCode")
    private String shortCode;

    @NonNull
    @Column(name = "generatedLink")
    private String generatedLink;

    public ShortLink(@NonNull String url, @NonNull String shortCode, @NonNull String generatedLink) {
        this.url = url;
        this.shortCode = shortCode;
        this.generatedLink = generatedLink;
    }
}
