package org.xujin.halo.validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import java.util.Locale;

/**
 * @author xujin
 * @date 2018/6/25
 */
public class HaloMessageInterpolator extends ResourceBundleMessageInterpolator{

    @Override
    public String interpolate(String message, Context context) {
        //Use English Locale
        String resolvedMessage = super.interpolate(message, context, Locale.ENGLISH);
        return resolvedMessage;
    }
}
