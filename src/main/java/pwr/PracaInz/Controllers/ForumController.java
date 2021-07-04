package pwr.PracaInz.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.PracaInz.Utils.UserAuthorizationUtilities;

@RestController
@RequestMapping("/forum")
public class ForumController {
    @GetMapping("/user")
    public void writeUser() {
        System.out.println(UserAuthorizationUtilities.getAuthorizationInfoOfCurrentUser());
        System.out.println(UserAuthorizationUtilities.getPrincipalOfCurrentUser());
    }
}
