package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.UserEnt;
import org.pl.model.User;

@ApplicationScoped
public class UserConverter {
    @Inject
    private AddressConverter addressConverter;
    @Inject
    private UserAccessTypeConverter clientAccessTypeConverter;

    public UserEnt convert(User user) {
        if (user == null) return null;
        try {
            return UserEnt
                    .builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .archive(user.getArchive())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .addressEnt(addressConverter.convert(user.getAddress()))
                    .userAccessType(clientAccessTypeConverter.convert(user.getUserAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User convert(UserEnt userEnt) {
        if (userEnt == null) return null;
        try {
            return User.builder()
                    .id(userEnt.getId())
                    .username(userEnt.getUsername())
                    .password(userEnt.getPassword())
                    .archive(userEnt.getArchive())
                    .firstName(userEnt.getFirstName())
                    .lastName(userEnt.getLastName())
                    .phoneNumber(userEnt.getPhoneNumber())
                    .address(addressConverter.convert(userEnt.getAddressEnt()))
                    .userAccessType(clientAccessTypeConverter.convert(userEnt.getUserAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
