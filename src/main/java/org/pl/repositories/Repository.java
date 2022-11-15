package org.pl.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Entity;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository<T extends Entity> {
    protected ArrayList<T> elements;

    public T create(T newElement) throws RepositoryException {
        for (int i = 0; i < elements.size(); ++i) {
            if (elements.get(i).equals(newElement)) {
                newElement.setArchive(false);
                return elements.set(i, newElement);
            }
        }
        if (elements.add(newElement)) {
            return newElement;
        } else {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public T read(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public T update(T newElement) throws RepositoryException {
        for (int i = 0; i < elements.size(); ++i) {
            if (elements.get(i).getId().equals(newElement.getId())) {
                return elements.set(i, newElement);
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public T delete(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getId().equals(id)) {
                element.setArchive(true);
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }
}
