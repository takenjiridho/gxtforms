package com.googlecode.gxtforms.client.utils;

import java.util.Collection;

public class CollectionUtils {
    
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

}
