package com.switchfully.digibooky.authorization.service;

import com.switchfully.digibooky.exception.AccessForbiddenException;
import com.switchfully.digibooky.exception.NotFoundException;
import com.switchfully.digibooky.exception.PasswordNotMatchException;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.UserRepository;
import com.switchfully.digibooky.user.domain.userAttribute.EmailPassword;
import com.switchfully.digibooky.user.domain.userAttribute.RoleFeature;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthorizationService {

    UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void hasFeature(RoleFeature feature, String authorisation) {
        EmailPassword emailPassword = getEmailPassword(authorisation);
        Optional<User> user = userRepository.getUserByEmail(emailPassword.getEmail());

        if (user.isEmpty()) {
            throw new NotFoundException("Email in authorization header not found");
        }

        if (!user.get().passwordMatch(emailPassword.getPassword())) {
            throw new PasswordNotMatchException("Email/password in authorization header doesn't match");
        }

        if (!user.get().hasFeature(feature)) {
            throw new AccessForbiddenException("Authenticated user have no access to this feature");
        }
    }

    private EmailPassword getEmailPassword(String authorization) {
        String decodedEmailAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodedEmailAndPassword.substring(0, decodedEmailAndPassword.indexOf(":"));
        String password = decodedEmailAndPassword.substring(decodedEmailAndPassword.indexOf(":") + 1);
        return new EmailPassword(email, password);
    }
}
