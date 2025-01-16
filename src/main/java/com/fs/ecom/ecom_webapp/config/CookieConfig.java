//package com.fs.ecom.ecom_webapp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.
//
//@Configuration
//public class CookieConfig {
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setSameSite("None"); // Use "None" for cross-origin cookies
//        serializer.setCookiePath("/");
//        serializer.setDomainNamePattern("yourdomain.com"); // Use this if your app spans subdomains
//        serializer.setSecure(true); // Ensure cookies are sent only over HTTPS
//        return serializer;
//    }
//}
