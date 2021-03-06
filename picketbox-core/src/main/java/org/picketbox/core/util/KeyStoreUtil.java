/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketbox.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import org.picketbox.core.PicketBoxMessages;

/**
 * Utility to handle Java Keystore
 *
 * @author anil Saldhana
 * @since Jan 12, 2009
 */
public class KeyStoreUtil {

    /**
     * Get the KeyStore
     *
     * @param keyStoreFile
     * @param storePass
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static KeyStore getKeyStore(File keyStoreFile, char[] storePass) throws GeneralSecurityException, IOException {
        FileInputStream fis = new FileInputStream(keyStoreFile);
        return getKeyStore(fis, storePass);
    }

    /**
     * Get the Keystore given the url to the keystore file as a string
     *
     * @param fileURL
     * @param storePass
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static KeyStore getKeyStore(String fileURL, char[] storePass) throws GeneralSecurityException, IOException {
        if (fileURL == null)
            throw PicketBoxMessages.MESSAGES.invalidNullArgument("fileURL");

        File file = new File(fileURL);
        FileInputStream fis = new FileInputStream(file);
        return getKeyStore(fis, storePass);
    }

    /**
     * Get the Keystore given the URL to the keystore
     *
     * @param url
     * @param storePass
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static KeyStore getKeyStore(URL url, char[] storePass) throws GeneralSecurityException, IOException {
        if (url == null)
            throw PicketBoxMessages.MESSAGES.invalidNullArgument("url");

        return getKeyStore(url.openStream(), storePass);
    }

    /**
     * Get the Key Store <b>Note:</b> This method wants the InputStream to be not null.
     *
     * @param ksStream
     * @param storePass
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws IllegalArgumentException if ksStream is null
     */
    public static KeyStore getKeyStore(InputStream ksStream, char[] storePass) throws GeneralSecurityException, IOException {
        if (ksStream == null)
            throw PicketBoxMessages.MESSAGES.invalidNullArgument("InputStream for the KeyStore");
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(ksStream, storePass);
        return ks;
    }

    /**
     * Generate a Key Pair
     *
     * @param algo (RSA, DSA etc)
     * @return
     * @throws GeneralSecurityException
     */
    public static KeyPair generateKeyPair(String algo) throws GeneralSecurityException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algo);
        return kpg.genKeyPair();
    }

    /**
     * Get the Public Key from the keystore
     *
     * @param ks
     * @param alias
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    public static PublicKey getPublicKey(KeyStore ks, String alias, char[] password) throws GeneralSecurityException {
        PublicKey publicKey = null;

        // Get private key
        Key key = ks.getKey(alias, password);
        if (key instanceof PrivateKey) {
            // Get certificate of public key
            Certificate cert = ks.getCertificate(alias);

            // Get public key
            publicKey = cert.getPublicKey();
        }
        // if alias is a certificate alias, get the public key from the certificate.
        if (publicKey == null) {
            Certificate cert = ks.getCertificate(alias);
            if (cert != null)
                publicKey = cert.getPublicKey();
        }
        return publicKey;
    }

    /**
     * Add a certificate to the KeyStore
     *
     * @param keystoreFile
     * @param storePass
     * @param alias
     * @param cert
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void addCertificate(File keystoreFile, char[] storePass, String alias, Certificate cert)
            throws GeneralSecurityException, IOException {
        KeyStore keystore = getKeyStore(keystoreFile, storePass);

        // Add the certificate
        keystore.setCertificateEntry(alias, cert);

        // Save the new keystore contents
        FileOutputStream out = new FileOutputStream(keystoreFile);
        keystore.store(out, storePass);
        out.close();
    }
}