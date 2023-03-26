package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.*;


@ApplicationScoped
public class ClientTypeConverter {
    public ClientTypeSoap convert(ClientType clientType) {
        if (clientType == null) return null;
        ClientTypeSoap clientTypeSoap = switch (clientType.getType()) {
            case "BASIC" -> new BasicSoap();
            case "PREMIUM" -> new PremiumSoap();
            case "VIP" -> new VipSoap();
            default -> throw new RuntimeException("Invalid client Type");
        };
        clientTypeSoap.setId(clientType.getId());
        return clientTypeSoap;
    }

    public ClientType convert(ClientTypeSoap clientTypeSoap) {
        if (clientTypeSoap == null) return null;
        ClientType clientType = switch (clientTypeSoap.getType()) {
            case "BASIC" -> new Basic();
            case "PREMIUM" -> new Premium();
            case "VIP" -> new Vip();
            default -> throw new RuntimeException("Invalid client Type");
        };
        clientType.setId(clientTypeSoap.getId());
        return clientType;
    }
}
