package org.pl.model;

import java.util.UUID;

public interface EntityRest {
    void setArchive(boolean archive);
    boolean isArchive();
    UUID getId();
}
