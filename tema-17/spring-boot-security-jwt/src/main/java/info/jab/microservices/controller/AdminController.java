package info.jab.microservices.controller;

import info.jab.microservices.model.CheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(
        path = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @GetMapping("/check")
    public @ResponseBody ResponseEntity<CheckResponse> check() {

        if(isAdmin()) {
            return ResponseEntity.ok().body(new CheckResponse("Ok"));
        }
        return ResponseEntity.ok().body(new CheckResponse("Ko"));
    }

    private boolean isAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Long counter = securityContext.getAuthentication().getAuthorities().stream()
                .map(user -> user.getAuthority())
                .filter(authority -> authority.equals("ADMIN"))
                .count();

        if(counter > 0) {
            return true;
        }
        return false;
    }
}
