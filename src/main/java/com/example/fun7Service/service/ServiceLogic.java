package com.example.fun7Service.service;

import com.example.fun7Service.model.ServiceStatus;
import com.example.fun7Service.datastore.UserDataStore;
import com.example.fun7Service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ServiceLogic {
    private UserDataStore userDataStore;

    public ServiceLogic() {
        userDataStore = new UserDataStore();
    }

    public ServiceStatus checkServices(String timezone, String userId, String countryCode) {
        ServiceStatus status = new ServiceStatus();

        // Validate the timezone
        if (!isValidTimezone(timezone)) {
            throw new RuntimeException("Timezone is not valid: " + timezone);
        }

        // Retrieve userGameUsage based on userId
        User user = userDataStore.getUser(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        int userGameUsage = user.getUserGameUsage();

        // Check for multiplayer
        boolean isMultiplayerEnabled = checkMultiplayerConditions(userGameUsage, countryCode);
        status.setMultiplayer(isMultiplayerEnabled ? "enabled" : "disabled");

        // Check for customer support
        boolean isCustomerSupportEnabled = checkCustomerSupportConditions(timezone);
        status.setCustomerSupport(isCustomerSupportEnabled ? "enabled" : "disabled");

        // Check for ads
        boolean isAdsEnabled = checkAdsConditions(countryCode);
        status.setAds(isAdsEnabled ? "enabled" : "disabled");

        return status;
    }

    private boolean checkMultiplayerConditions(int userGameUsage, String countryCode) {
        boolean usedGameMoreThan5Times = userGameUsage  > 5;
        boolean isFromUS = "US".equals(countryCode);

        return usedGameMoreThan5Times && isFromUS;
    }

    private boolean checkCustomerSupportConditions(String timezone) {
        if (!"Europe/Ljubljana".equals(timezone)) {
            return false;
        }

        ZoneId userZone = ZoneId.of(timezone);
        ZonedDateTime currentTime = ZonedDateTime.now(userZone);

        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(15, 0);

        return currentTime.getDayOfWeek().getValue() >= 1 && currentTime.getDayOfWeek().getValue() <= 5 &&
                currentTime.toLocalTime().isAfter(startTime) && currentTime.toLocalTime().isBefore(endTime);
    }

    private boolean checkAdsConditions(String countryCode) {
        String partnerApiUrl = "https://us-central1-o7tools.cloudfunctions.net/fun7-ad-partner?countryCode=" + countryCode;
        String username = "fun7user";
        String password = "fun7pass";

        RestTemplate restTemplate = new RestTemplate();
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            restTemplate.getInterceptors().add(
                    new BasicAuthenticationInterceptor(username, password)
            );

            String response = restTemplate.getForObject(partnerApiUrl, String.class);

            if (response != null && response.contains("ads: sure, why not!")) {
                return true;
            }
        } catch (HttpClientErrorException e) {
            // Handle client errors (4xx)
            logger.error("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            // Handle server errors (5xx)
            logger.error("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("An error occurred: " + e.getMessage());
        }

        return false;
    }

    private boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
