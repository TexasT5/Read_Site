package Util;

import org.jsoup.nodes.Document;

import java.io.IOException;

public class UJsoup {
    public Document getDiv(String getURL) throws IOException {
        return org.jsoup.Jsoup.connect(getURL).ignoreContentType(true).get();
    }
}
