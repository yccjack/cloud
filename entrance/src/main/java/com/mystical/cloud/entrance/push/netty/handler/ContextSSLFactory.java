package com.mystical.cloud.entrance.push.netty.handler;

import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;


public class ContextSSLFactory {

    private static final SSLContext SSL_CONTEXT_S ;

    static{
        SSLContext sslContextServer = null ;
        try {
            sslContextServer = SSLContext.getInstance("SSLv3") ;

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        try{
            if(getKeyManagersServer() != null && getTrustManagersServer() != null ){
                sslContextServer.init(getKeyManagersServer(), getTrustManagersServer(), null);
            }


        }catch(Exception e){
            e.printStackTrace() ;
        }
        sslContextServer.createSSLEngine().getSupportedCipherSuites() ;
        SSL_CONTEXT_S = sslContextServer ;
    }
    public ContextSSLFactory(){

    }
    public static SSLContext getSslContext(){
        return SSL_CONTEXT_S ;
    }
    /**
     * 获取服务端信任的证书
     * @param:             @return
     * @return:         TrustManager[]
     * @throws
     */
    private static TrustManager[] getTrustManagersServer(){
        FileInputStream is = null ;
        TrustManager[] trustManagersw=null;
        TrustManagerFactory trustManagerFactory=null;
        KeyStore ks=null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance("SunX509") ;
            is =new FileInputStream( (new ClassPathResource("ssl/serverCerts.jks")).getFile() );
            String keyStorePass = "123456" ;
            ks=KeyStore.getInstance("JKS");
            ks.load(is,keyStorePass.toCharArray());
            trustManagerFactory.init(ks);
            trustManagersw=trustManagerFactory.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(is != null ){
                try {
                    is.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trustManagersw;
    }


    /**
     * 获取keymanager列表
     * @param:             @return
     * @return:         KeyManager[]
     * @throws
     */
    private static KeyManager[] getKeyManagersServer(){
        FileInputStream is = null ;
        KeyStore ks = null ;
        KeyManagerFactory keyFac = null ;

        KeyManager[] kms = null ;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509") ;
//            String keyStorePath = PropertyUtil.getProperty("httpsKeyStorePath");
            is =new FileInputStream( (new ClassPathResource("ssl/serverCerts.jks")).getFile() );
            ks = KeyStore.getInstance("JKS") ;
            String keyStorePass = "123456";
            ks.load(is , keyStorePass.toCharArray()) ;
            keyFac.init(ks, keyStorePass.toCharArray()) ;
            kms = keyFac.getKeyManagers() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(is != null ){
                try {
                    is.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms ;
    }
}