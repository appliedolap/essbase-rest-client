package com.appliedolap.essbase.util;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.client.ApiException;

import java.util.concurrent.Callable;

public class WrapperUtil {

    public static <T> T doWithWrap(Callable<T> func) throws EssApiException {
        try {
            return func.call();
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

    public static void wrap(EssApiRunnable runnable) throws EssApiException {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

    @FunctionalInterface
    public interface EssApiRunnable {

        void run() throws ApiException;

    }


}