package com.console.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UrlUtils {
    public static URL createUrl(String url) throws MalformedURLException {
        return URI.create(url).toURL();
    }
}
