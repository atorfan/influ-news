package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rescale_image_task")
public class RescaleImageTaskJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 7624402735807046987L;

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private LocalDateTime createdAt;
    private String originalImageHash;
    private int width;
    private int height;
    private String imageUrl;

    public RescaleImageTaskJpaEntity() {
    }

    public RescaleImageTaskJpaEntity(UUID taskId, LocalDateTime createdAt, String originalImageHash, int width, int height, String imageUrl) {
        this.id = taskId;
        this.createdAt = createdAt;
        this.originalImageHash = originalImageHash;
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOriginalImageHash() {
        return originalImageHash;
    }

    public void setOriginalImageHash(String originalImageHash) {
        this.originalImageHash = originalImageHash;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RescaleImageTaskJpaEntity that = (RescaleImageTaskJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(originalImageHash, that.originalImageHash) &&
                Objects.equals(createdAt, that.createdAt) && Objects.equals(originalImageHash, that.originalImageHash) &&
                Objects.equals(width, that.width) && Objects.equals(height, that.height) &&
                Objects.equals(imageUrl, that.imageUrl) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, originalImageHash, createdAt, originalImageHash, width, height, imageUrl, imageUrl);
    }

    @Override
    public String toString() {
        return "RescaleImageTaskJpaEntity{" +
                "id=" + id +
                ", expectedTimestamp=" + createdAt +
                ", imageHash='" + originalImageHash + '\'' +
                ", createdAt=" + createdAt +
                ", originalImageHash='" + originalImageHash + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", testImagePath='" + imageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
