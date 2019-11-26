package com.cashmanager.cashmanager.controller;

import com.cashmanager.cashmanager.model.Article;
import com.cashmanager.cashmanager.model.Authentification;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

@RestController
@RequestMapping("/api")
public class AuthentificationController {

    @PostMapping("/auth")
    public Authentification createAuthToken(@Valid @RequestBody PasswordAuth myPwd) throws IOException, JSONException {
        JSONObject obj = new JSONObject();
        Authentification myAuth = null;
        boolean status = this.checkPassword(myPwd.getPassword());

        if (status) {
            byte[] randomBytes = new byte[24];
            SecureRandom secureRandom = new SecureRandom();
            Base64.Encoder baseEncoder = Base64.getUrlEncoder();

            secureRandom.nextBytes(randomBytes);
            myAuth = new Authentification(baseEncoder.encodeToString(randomBytes), true);
        } else
            myAuth = new Authentification("", false);

        return myAuth;
    }

    public boolean checkPassword(String myCurrentPwd) throws IOException {
        InputStream in = null;

        try {
            Properties prop = new Properties();
            String propFileName = "cashmanager.conf";

            in = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (in != null) {
                prop.load(in);
            } else {
                throw new FileNotFoundException("config file '" + propFileName + "' not found");
            }
            String pwd = prop.getProperty("PASSWORD_AUTH");

            return myCurrentPwd.equals(pwd);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            assert in != null;
            in.close();
        }

        return false;
    }

    public static class PasswordAuth {
        private int deviceid;
        private String password;

        public String getPassword() {
            return this.password;
        }

        public int getDeviceid() {
            return this.deviceid;
        }
    }
}
