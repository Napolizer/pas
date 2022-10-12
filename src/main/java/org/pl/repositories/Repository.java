package org.pl.repositories;

import lombok.Data;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Entity;

import java.util.ArrayList;
@Data
public class Repository<T extends Entity> {
    protected ArrayList<T> elements;

    public void add(T element) throws RepositoryException {
        if (element == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ADD_INVALID_EXCEPTION);
        }
        for (T t : elements) {
            if (t == element) {
                t.setArchive(false);
                return;
            }
        }
        elements.add(element);
    }

    public void archivise(int ID) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == ID && element.isArchive() == false) {
                element.setArchive(true);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }

    T get(int ID) throws RepositoryException {
        if (ID < 0) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_INVALID_INPUT_EXCEPTION);
        }
        if (elements.size() == 0) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        for (T element : elements) {
            if (element.getID() == ID) {
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
    
    int getSize(boolean present) throws RepositoryException {
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

    public boolean isArchive(int ID) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == ID) {
                return element.isArchive();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void unarchivise(int ID) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == ID) {
                element.setArchive(false);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }
}
