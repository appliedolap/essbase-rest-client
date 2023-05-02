package com.appliedolap.essbase.util;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.client.ApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <R, T> R wrapFunc(Callable<T> func, Function<? super T, ? extends R> mapper) throws EssApiException {
        try {
            T originalReturnValue = func.call();
            return mapper.apply(originalReturnValue);
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

    public static <R, T> List<R> wrapList(Callable<List<T>> func, Function<? super T, ? extends R> mapper) throws EssApiException {
        try {
            List<T> returnValues = func.call();
            List<R> converted = new ArrayList<R>();
            for (T returnValue : returnValues) {
                converted.add(mapper.apply(returnValue));
            }
            return converted;
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

    public static <R, T> List<R> wrapToList(List<T> sourceList, Function<? super T, ? extends R> mapper) throws EssApiException {
        try {
            if (sourceList == null) return Collections.emptyList();
            return sourceList.stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new EssApiException(e);
        }
    }

    @FunctionalInterface
    public interface EssApiRunnable {

        void run() throws ApiException;

    }


}