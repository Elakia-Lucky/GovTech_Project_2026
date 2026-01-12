package com.govtech.service;

import com.govtech.entity.Session;
import com.govtech.entity.User;
import com.govtech.exception.GovtechException;
import com.govtech.repository.SessionRepository;
import com.govtech.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public Session createSession(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new GovtechException("User '" + username + "' is not allowed to initiate a session");
        }

        Session session = new Session(userOpt.get());
        return sessionRepository.save(session);
    }


    @Transactional(readOnly = true)
    public Session getSession(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new GovtechException("Session not found: " + sessionId));
    }


    @Transactional
    public void closeSession(Session session) {
        session.setStatus(Session.Status.CLOSED);
        sessionRepository.save(session);
    }
}
