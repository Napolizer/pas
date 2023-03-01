package org.pl.providers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.Serializable;

@ApplicationScoped
public class ETagProvider {
    @Inject
    @ConfigProperty(name="etag_secret_key", defaultValue = "Default etag secret key")
    private String secretKey;

    private HmacUtils hmacUtils;

    @PostConstruct
    public void init() {
        hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey);
    }

    public String generateETag(Serializable serializable) {
        byte[] data = SerializationUtils.serialize(serializable);
        return hmacUtils.hmacHex(data);
    }
}
