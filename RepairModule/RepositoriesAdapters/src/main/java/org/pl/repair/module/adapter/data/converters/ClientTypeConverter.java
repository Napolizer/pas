package org.pl.repair.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.repair.module.adapter.data.model.BasicEnt;
import org.pl.repair.module.adapter.data.model.ClientTypeEnt;
import org.pl.repair.module.adapter.data.model.PremiumEnt;
import org.pl.repair.module.adapter.data.model.VipEnt;
import org.pl.repair.module.model.Basic;
import org.pl.repair.module.model.ClientType;
import org.pl.repair.module.model.Premium;
import org.pl.repair.module.model.Vip;

@ApplicationScoped
public class ClientTypeConverter {
    public ClientTypeEnt convert(ClientType clientType) {
        if (clientType == null) return null;
        ClientTypeEnt clientTypeEnt = switch (clientType.getType()) {
            case "BASIC" -> new BasicEnt();
            case "PREMIUM" -> new PremiumEnt();
            case "VIP" -> new VipEnt();
            default -> throw new RuntimeException("Invalid client Type");
        };
        clientTypeEnt.setId(clientType.getId());
        return clientTypeEnt;
    }

    public ClientType convert(ClientTypeEnt clientTypeEnt) {
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
