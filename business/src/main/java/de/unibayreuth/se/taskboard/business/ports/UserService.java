package de.unibayreuth.se.taskboard.business.ports;

import java.util.List;
import java.util.UUID;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import edu.umd.cs.findbugs.annotations.NonNull;

public interface UserService {
    void clear();
    @NonNull
    User create(@NonNull User user) throws MalformedRequestException;
    @NonNull
    List<User> getAll();
    @NonNull
    User getById(@NonNull UUID id) throws UserNotFoundException;
    @NonNull
    User upsert(@NonNull User user) throws UserNotFoundException;
}
