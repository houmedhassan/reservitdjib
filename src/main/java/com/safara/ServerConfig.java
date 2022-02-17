//package com.safara;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ServerConfig {
//
//    @Bean
//    public EmbeddedServletContainerFactory webServerFactory() {
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//            @Override
//            public void customize(Connector connector) {
//                connector.setProperty("relaxedQueryChars", "|{}[]");
//            }
//        });
//        return factory;
//    }
//
//}