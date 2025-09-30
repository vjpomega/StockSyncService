package com.omega.stocksync.common;

import static com.omega.stocksync.constants.AppConstants.UNIVERSAL_TIME_ZONE;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Data;

@Embeddable
@Data
public class AuditInfo {
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  @PrePersist
  public void onCreate() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of(UNIVERSAL_TIME_ZONE));
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  public void onUpdate() {
    this.updatedAt = ZonedDateTime.now(ZoneId.of(UNIVERSAL_TIME_ZONE));
  }
}
