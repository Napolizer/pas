package org.pl.adapter.data.model;

import java.util.UUID;

public interface EntityEnt {
    void setArchive(boolean archive);
    boolean isArchive();
    UUID getId();
}
