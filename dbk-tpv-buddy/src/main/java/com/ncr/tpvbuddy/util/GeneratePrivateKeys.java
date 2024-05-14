package com.ncr.tpvbuddy.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GeneratePrivateKeys {
    public static void main(String... args) {

        try {

            // Make object of key pair generator using RSA algorithm
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            OutputStream out;
            //set size of key
            kpg.initialize(2048);

            //generate pair of public and private keys
            KeyPair kp = kpg.generateKeyPair();

            //make public and private keys
            Key pub = kp.getPublic();
            Key pvt = kp.getPrivate();

            System.out.println("Generated Public key :- " + pub);
            System.out.println("Generated Private key :- " + pvt);

            //saving keys in binary format

            String outFile = "public";
            out = new FileOutputStream(outFile + ".key");
            out.write(pvt.getEncoded());
            out.close();

            out = new FileOutputStream(outFile + ".pub");
            out.write(pvt.getEncoded());
            out.close();

            // prints "Private key format"
            System.err.println("Private key format in which it is created: " + pvt.getFormat());

            // prints "Public key format"
            System.err.println("Public key format in which it is created: " + pub.getFormat());


        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
