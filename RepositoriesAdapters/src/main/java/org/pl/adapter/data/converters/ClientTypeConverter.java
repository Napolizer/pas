package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.adapter.data.model.BasicEnt;
import org.pl.adapter.data.model.ClientTypeEnt;
import org.pl.adapter.data.model.PremiumEnt;
import org.pl.adapter.data.model.VipEnt;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

@ApplicationScoped
public class ClientTypeConverter {
    public ClientTypeEnt convert(ClientType clientType) {
        return switch (clientType.getType()) {
            case "BASIC" -> new BasicEnt();
            case "PREMIUM" -> new PremiumEnt();
            case "VIP" -> new VipEnt();
            default -> throw new RuntimeException("Invalid client Type");
        };
    }

    public ClientType convert(ClientTypeEnt clientTypeEnt) {
        return switch (clientTypeEnt.getType()) {
            case "BASIC" -> new Basic();
            case "PREMIUM" -> new Premium();
            case "VIP" -> new Vip();
            default -> throw new RuntimeException("Invalid client Type");
        };
    }
}
