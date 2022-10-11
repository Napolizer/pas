package org.pl.exceptions;

import lombok.ToString;

@ToString
public class RepositoryException extends Exception {
    public static final String REPOSITORY_ADD_INVALID_EXCEPTION = "Invalid attempt to add object to repository.";
    public static final String REPOSITORY_ARCHIVE_EXCEPTION = "Trying to archive archived object.";
    public static final String REPOSITORY_GET_EXCEPTION = "Object with given ID does not exists in the repository.";
    public static final String REPOSITORY_GET_INVALID_INPUT_EXCEPTION = "Attempt to find object with negative ID.";
    public static final String REPOSITORY_CLIENT_IS_ARCHIVE_EXCEPTION = "Attempt to repair archived client.";
    public static final String REPOSITORY_HARDWARE_IS_ARCHIVE_EXCEPTION = "Attempt to repair archived hardware.";
    public static final String REPOSITORY_MAX_REPAIRS_EXCEED = "Given client type achieved max amount of repairs.";

    public RepositoryException(String message) {
        super(message);
    }
}
