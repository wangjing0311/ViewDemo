package com.ylw.viewdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PmsHookApplication extends ViewApplication implements InvocationHandler {

    private static final int GET_SIGNATURES = 64;
    private String appPkgName = "";
    private Object base;
    private byte[][] sign;

    private void hook(Context paramContext) {
        try {
            ByteArrayInputStream localObject1 = new ByteArrayInputStream(Base64.decode("AQAAA2MwggNfMIICR6ADAgECAgRYniplMA0GCSqGSIb3DQEBCwUAMF8xDDAKBgNVBAYTAzAxMDEQ\nMA4GA1UECBMHYmVpamluZzEQMA4GA1UEBxMHYmVpamluZzEOMAwGA1UEChMFZGVuaXUxDjAMBgNV\nBAsTBWRlbml1MQswCQYDVQQDEwJkYTAgFw0xNzExMzAwMjM5NDdaGA8yMjE3MTAxMzAyMzk0N1ow\nXzEMMAoGA1UEBhMDMDEwMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMQ4wDAYD\nVQQKEwVkZW5pdTEOMAwGA1UECxMFZGVuaXUxCzAJBgNVBAMTAmRhMIIBIjANBgkqhkiG9w0BAQEF\nAAOCAQ8AMIIBCgKCAQEAkMgZC6fHNwqn24QmXvG5zKk2wP3nDttQja4OIBVNWokdCk7PxU1NTFwD\nJfqAzwNisfBEYrzRqrqagF0n65qPshLSvoCehk+Hif1GYGgVCtjSxZ28oC0KCy/bObikYBWs6Szv\ng5MAucnsBQ1hSv5GyxXGFPpIrJjtByhUs2qGRhFtk6DWZR0s15QRGKGTja0uwC+h8Fggm66FYfzR\nP7Hv1f2wt0JvVvXv1FqU4AMzj73tINkOzpHCvpYM43aD79ba/4lRscLEXYGFhcXHaLfezA0zYud/\ndeSQWDrIc6bOvhljPOvBBK5MYtUkjCpaUmrF1/GNa7HB5zw64MVRhDVZAQIDAQABoyEwHzAdBgNV\nHQ4EFgQU+v8qqE52xpTwgRlO0cEeVA2hB3wwDQYJKoZIhvcNAQELBQADggEBABpDK6NI4eEC+5Yh\nyYrsdvdH1PXFWgNz84NBP0Cm9hZsvJvUIQ6vT9x9by6FrGHPojUsZjGYyphYnExP9vuQ+epv9gs1\noBXf7HSCB2D8D4oo40WDgH0SCB4WDSsyfsTRDnLR/nSvX49KRjDDoeMBBH9EAjJZSKqsAWPxhRov\n5+oZSh6ZpZRY5OWt1Q0/Wmg7tVVIri8Qr9JlgrVx174KcfcThXvuWYg4dM1i/ZhgKscgGuCJnIXO\nLkJ1/dddlVuqgsYJE6eIeHjWlk6Vib6hRq0bJ/FBfPifzA66dxKzpqExCHEK7YcTi7M81ZHKcdkU\nq935CiHwlenVxKINjhkXECQ=\n", 0));
            DataInputStream localObject2 = new DataInputStream(localObject1);
            byte[][] localObject11 = new byte[localObject2.read() & 0xFF][];
            for (int i = 0; i < (localObject11).length; i++) {
                localObject11[i] = new byte[localObject2.readInt()];
                localObject2.readFully(localObject11[i]);
            }
            Class localObject3 = Class.forName("android.app.ActivityThread");
            Object localObject21 = localObject3.getDeclaredMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field localObject31 = localObject3.getDeclaredField("sPackageManager");
            localObject31.setAccessible(true);
            Object localObject4 = localObject31.get(localObject21);
            Class localClass = Class.forName("android.content.pm.IPackageManager");
            this.base = localObject4;
            this.sign = localObject11;
            this.appPkgName = paramContext.getPackageName();
            Object localObject111 = Proxy.newProxyInstance(localClass.getClassLoader(), new Class[]{localClass}, this);
            localObject31.set(localObject21, localObject111);
            localObject21 = paramContext.getPackageManager();
            Field paramContext1 = localObject21.getClass().getDeclaredField("mPM");
            paramContext1.setAccessible(true);
            paramContext1.set(localObject21, localObject111);
            System.out.println("PmsHook success.");
        } catch (Exception e) {
            while (true) {
                System.err.println("PmsHook failed.");
                e.printStackTrace();
            }
        }
    }

    protected void attachBaseContext(Context paramContext) {
        hook(paramContext);
        super.attachBaseContext(paramContext);
    }

    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
            throws Throwable {
        if ("getPackageInfo".equals(paramMethod.getName())) {
            paramObject = paramArrayOfObject[0];
            if (((((Integer) paramArrayOfObject[1]).intValue() & 0x40) != 0) && (this.appPkgName.equals(paramObject))) {
                PackageInfo paramMethod1 = (PackageInfo) paramMethod.invoke(this.base, paramArrayOfObject);
                paramMethod1.signatures = new Signature[this.sign.length];
                for (int i = 0; ; i++) {
                    paramObject = paramMethod1;
                    if (i >= paramMethod1.signatures.length) {
                        break;
                    }
                    paramMethod1.signatures[i] = new Signature(this.sign[i]);
                }
            }
        }
        paramObject = paramMethod.invoke(this.base, paramArrayOfObject);
        return paramObject;
    }
}
