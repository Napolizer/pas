package org.pl.model;

import java.util.UUID;

public interface EntityRest {
    void setArchive(Boolean archive);
    boolean isArchive();
    UUID getId();
}
