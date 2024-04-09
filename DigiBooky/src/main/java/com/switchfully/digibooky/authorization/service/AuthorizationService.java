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

@Service
public class AuthorizationService {

    UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void hasFeature(RoleFeature feature, String authorisation) {
        EmailPassword emailPassword = getEmailPassword(authorisation);
        User user = getUserByEmailPassword(emailPassword);

        if (!user.passwordMatch(emailPassword.getPassword())) {
            throw new PasswordNotMatchException("Email/password in authorization header doesn't match");
        }

        if (!user.hasFeature(feature)) {
            throw new AccessForbiddenException("Authenticated user have no access to this feature");
        }
    }

    public void isSameUser(String userId, String authorization) {
        EmailPassword emailPassword = getEmailPassword(authorization);
        User user = getUserByEmailPassword(emailPassword);
        if (!user.getId().equals(userId)) {
            throw new AccessForbiddenException("Authenticated user cannot lend a book for another member");
        }
    }

    private EmailPassword getEmailPassword(String authorization) {
        String decodedEmailAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodedEmailAndPassword.substring(0, decodedEmailAndPassword.indexOf(":"));
        String password = decodedEmailAndPassword.substring(decodedEmailAndPassword.indexOf(":") + 1);
        return new EmailPassword(email, password);
    }

    private User getUserByEmailPassword(EmailPassword emailPassword) {
        return userRepository.getUserByEmail(emailPassword.getEmail())
                .orElseThrow(() -> new NotFoundException("Email in authorization header not found"));
    }
}
