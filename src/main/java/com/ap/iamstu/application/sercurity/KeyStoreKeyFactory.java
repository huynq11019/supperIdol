package com.ap.iamstu.application.sercurity;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;

public class KeyStoreKeyFactory {

    private final Object lock = new Object();
    private Resource resource;
    private char[] password;
    private KeyStore store;
    private String type;

    public KeyStoreKeyFactory(Resource resource, char[] password) {
        this(resource, password, type(resource));
    }

    private KeyStoreKeyFactory(Resource resource, char[] password, String type) {
        this.resource = resource;
        this.password = password;
        this.type = type;
    }

    private static String type(Resource resource) {
        String ext = StringUtils.getFilenameExtension(resource.getFilename());
        return ext == null ? "jks" : ext;
    }

    public KeyPair getKeyPair(String alias) {
        return getKeyPair(alias, password);
    }

    private KeyPair getKeyPair(String alias, char[] password) {
        try {
            synchronized (lock) {
                if (store == null) {
                    synchronized (lock) {
                        store = KeyStore.getInstance(type);
                        try (InputStream stream = resource.getInputStream()) {
                            store.load(stream, this.password);
                        }
                    }
                }
            }
            RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(alias, password);
            Certificate certificate = store.getCertificate(alias);
            PublicKey publicKey = null;
            if (certificate != null) {
                publicKey = certificate.getPublicKey();
            } else if (key != null) {
                RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(),
                        key.getPublicExponent());
                publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            }
            return new KeyPair(publicKey, key);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: " + resource, e);
        }
    }
}
