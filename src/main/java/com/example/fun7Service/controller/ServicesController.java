package com.example.fun7Service.controller;

import com.example.fun7Service.model.ServiceStatus;
import com.example.fun7Service.service.ServiceLogic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServicesController {

    @GetMapping("/check-services")
    public ResponseEntity<ServiceStatus> checkServices(
            @RequestParam String timezone,
            @RequestParam String userId,
            @RequestParam String cc
    ) {
        ServiceStatus status = invokeServiceLogic(timezone, userId, cc);
        return ResponseEntity.ok(status);
    }

    private ServiceStatus invokeServiceLogic(String timezone, String userId, String countryCode) {
        ServiceLogic serviceLogic = new ServiceLogic();
        return serviceLogic.checkServices(timezone, userId, countryCode);
    }
}
