package org.pl.gateway.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.gateway.module.model.BasicRest;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.PremiumRest;
import org.pl.gateway.module.model.VipRest;


@ApplicationScoped
public class ClientTypeConverter {
    public ClientTypeRest convert(ClientType clientType) {
        if (clientType == null) return null;
        ClientTypeRest clientTypeEnt = switch (clientType.getType()) {
            case "BASIC" -> new BasicRest();
            case "PREMIUM" -> new PremiumRest();
            case "VIP" -> new VipRest();
            default -> throw new RuntimeException("Invalid client Type");
        };
        clientTypeEnt.setId(clientType.getId());
        return clientTypeEnt;
    }

    public ClientType convert(ClientTypeRest clientTypeEnt) {
        if (clientTypeEnt == null) return null;
        ClientType clientType = switch (clientTypeEnt.getType()) {
            case "BASIC" -> new Basic();
            case "PREMIUM" -> new Premium();
            case "VIP" -> new Vip();
            default -> throw new RuntimeException("Invalid client Type");
        };
        clientType.setId(clientTypeEnt.getId());
        return clientType;
    }
}
