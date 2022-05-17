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
                    String[] splited = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__=");
                    if(splited[0].isEmpty()){
                        String[] splitWindow = splited[1].trim().split("window\\.TYPageName=");
                        //System.out.println(splitWindow[0]);
                        JSONObject jsonObject = new JSONObject(splitWindow[0].trim());
                        jsonObject.getJSONObject("product").toMap().forEach((s, o) -> {
                            JSONObject jsonObject1 = new JSONObject(o);
                            if(jsonObject1.get(s) != null){
                                System.out.println(jsonObject1.get(s));
                            }
                        });
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
