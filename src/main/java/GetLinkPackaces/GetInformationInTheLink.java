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
                    String[] splited = dataNode.getWholeData().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if(splited[0].isEmpty()){
                        String[] splired = dataNode.getWholeData().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__=");
                        if(splired[0].isEmpty()){
                            //System.out.println(splired[1]);
                            JSONObject jsonObject = new JSONObject(splired[1]);
                            System.out.println(jsonObject.toString());

                        }
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
