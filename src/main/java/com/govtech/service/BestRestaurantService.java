package com.govtech.service;

import com.govtech.entity.RestaurantOption;
import com.govtech.entity.Session;
import com.govtech.exception.GovtechException;
import com.govtech.repository.RestaurantOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class BestRestaurantService {
	
	private static final String SESSION_CLOSED = "Session is closed.";
	private static final String NO_OPTION = "No options found.";

    private final RestaurantOptionRepository restaurantOptionRepository;
    private final SessionService sessionService;
    private final Random random = new Random();

    public BestRestaurantService(RestaurantOptionRepository restaurantOptionRepository,
                             SessionService sessionService) {
        this.restaurantOptionRepository = restaurantOptionRepository;
        this.sessionService = sessionService;
    }


    @Transactional
    public void submitRestaurant(UUID sessionId, String restaurant, String submittedBy) {
        Session session = sessionService.getSession(sessionId);

        if (session.getStatus() == Session.Status.CLOSED) {
            throw new GovtechException(SESSION_CLOSED);
        }

        RestaurantOption choice = new RestaurantOption(restaurant, submittedBy, session);
        restaurantChoiceRepository.save(choice);
    }


    @Transactional
    public String pickRandom(UUID sessionId) {
        Session session = sessionService.getSession(sessionId);

        if (session.getStatus() == Session.Status.CLOSED) {
            throw new GovtechException(SESSION_CLOSED);
        }

        List<RestaurantOption> options = RestaurantOptionRepository.findBySession(session);
        if (options.isEmpty()) {
            throw new GovtechException(NO_OPTION);
        }

        // To pick the random restaurant
        RestaurantOption selected = options.get(random.nextInt(options.size()));

        //To close the session 
        sessionService.closeSession(session);

        return selected.getRestaurantName();
    }
}
