package org.pl.user.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.user.module.model.User;
import org.pl.user.module.model.UserRest;

@ApplicationScoped
public class UserConverter {
    @Inject
    private UserAccessTypeConverter userAccessTypeConverter;
    @Inject
    private AddressConverter addressConverter;

    public UserRest convert(User user) {
        if (user == null) return null;
        return UserRest.builder()
                .id(user.getId())
                .username(user.getUsername())
                .archive(user.getArchive())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .address(addressConverter.convert(user.getAddress()))
                .clientAccessType(userAccessTypeConverter.convert(user.getUserAccessType()))
                .password(user.getPassword())
                .build();
    }

    public User convert(UserRest userRest) {
        if (userRest == null) return null;
        return User.builder()
                .id(userRest.getId())
                .username(userRest.getUsername())
                .archive(userRest.getArchive())
                .firstName(userRest.getFirstName())
                .lastName(userRest.getLastName())
                .phoneNumber(userRest.getPhoneNumber())
                .address(addressConverter.convert(userRest.getAddress()))
                .userAccessType(userAccessTypeConverter.convert(userRest.getClientAccessType()))
                .password(userRest.getPassword())
                .build();
    }
}
