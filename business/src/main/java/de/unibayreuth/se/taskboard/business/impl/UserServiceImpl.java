package de.unibayreuth.se.taskboard.business.impl;

import java.util.List;
import java.util.UUID;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.UserPersistenceService;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserPersistenceService userPersistenceService;

    public UserServiceImpl(UserPersistenceService userPersistenceService) {
        this.userPersistenceService = userPersistenceService;
    }

    @Override
    public void clear() {
        userPersistenceService.clear();
    }
    @Override
    public User create(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User ID must not be set.");
        }
        return upsert(user);
    }

    @Override
    public List<User> getAll() {
        return userPersistenceService.getAll();
    }

    @Override
    public User getById(UUID id) {
        return userPersistenceService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " does not exist."));
    }

    @Override
    public User upsert(User user) {
        return userPersistenceService.upsert(user);
    }
    
}
