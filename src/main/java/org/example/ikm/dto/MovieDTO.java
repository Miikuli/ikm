package org.example.ikm.dto;

import java.time.LocalDate;
import java.util.Set;

public class MovieDTO {
    private String title;
    private LocalDate releaseDate;
    private DurationDTO duration; // Используем DurationDTO
    private Set<Short> authorIds;

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public DurationDTO getDuration() {
        return duration;
    }

    public void setDuration(DurationDTO duration) {
        this.duration = duration;
    }

    public Set<Short> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Short> authorIds) {
        this.authorIds = authorIds;
    }
}