package org.pl.model;

import java.util.UUID;

public interface Entity {
    void setArchive(boolean archive);
    boolean isArchive();
    UUID getId();
}
