package org.pl.model;

import java.util.UUID;

public interface Entity {
    void setArchive(Boolean archive);
    boolean isArchive();
    UUID getId();
}
