package org.pl.gateway.module.model;

import java.util.UUID;

public interface Entity {
    void setArchive(Boolean archive);
    Boolean getArchive();
    UUID getId();
}
