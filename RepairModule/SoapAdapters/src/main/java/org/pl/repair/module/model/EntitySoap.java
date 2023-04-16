package org.pl.repair.module.model;

import java.util.UUID;

public interface EntitySoap {
    void setArchive(Boolean archive);
    Boolean getArchive();
    UUID getId();
}
