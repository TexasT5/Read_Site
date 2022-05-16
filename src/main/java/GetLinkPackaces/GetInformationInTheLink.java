package GetLinkPackaces;

import Util.UJsoup;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetInformationInTheLink {
    public void getInformationInTheLink(String link , String getBrand){
        switch (getBrand){
            case "Trendyol":
                 getInformationTrendyolProducts(link);
                break;
            default: break;
        }
    }

    private void getInformationTrendyolProducts(String links){
        try {
            Elements elements = new UJsoup().getDiv(links).getElementsByTag("body");
            elements.forEach(element -> {
                element.getElementsByTag("script").dataNodes().forEach(dataNode -> {
                    String[] splited = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if (splited[0].isEmpty()) {
                        String[] splitProductDetail = splited[1].trim().split("=");
                        if(splitProductDetail[0].isEmpty()){
                            String splitBarcode = splitProductDetail[1].trim().split("\"barcode\":")[1];
                            System.out.println(links + splitBarcode.split(",")[0]);
                       }
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
