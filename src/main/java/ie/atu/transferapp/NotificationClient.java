package ie.atu.transferapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "http://localhost:8083/notification")
interface NotificationClient {

    @PostMapping("/send")
    void sendNotification(@RequestParam String message);
}
