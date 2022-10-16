package org.pl.repositories;

import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

import java.util.ArrayList;

@Data
@Builder
public class ClientRepository {
    private final ArrayList<Client> elements;

    public void add(Client element) throws RepositoryException {
        if (element == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ADD_INVALID_EXCEPTION);
        }
        for (Client t : elements) {
            if (t == element) {
                t.setArchive(false);
                return;
            }
        }
        elements.add(element);
    }
    public void archivise(int id) throws RepositoryException {
        for (Client element : elements) {
            if (element.getID() == id && !element.isArchive()) {
                element.setArchive(true);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }

    public Client get(int id) throws RepositoryException {
        if (id < 0) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_INVALID_INPUT_EXCEPTION);
        }
        if (elements.isEmpty()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        for (Client element : elements) {
            if (element.getID() == id) {
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public int getSize(boolean present) throws RepositoryException {
        int output = 0;
        if (present) {
            for (int i = 0; i < elements.size(); i++) {
                if (!get(i).isArchive())
                    output++;
            }
        } else {
            for (int i = 0; i < elements.size(); i++) {
                if (get(i).isArchive())
                    output++;
            }
        }
        return output;
    }

    public boolean isArchive(int id) throws RepositoryException {
        for (Client element : elements) {
            if (element.getID() == id) {
                return element.isArchive();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void unarchivise(int id) throws RepositoryException {
        for (Client element : elements) {
            if (element.getID() == id) {
                element.setArchive(false);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }
}