package GetLinkPackaces;

import Model.Trendyol.Product;
import Model.Trendyol.TrendyolModel;
import Util.UJsoup;
import Writer.WriteFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class GetInformationInTheLink {
    public void getInformationInTheLink(String link , String getBrand) throws IOException {
        switch (getBrand){
            case "Trendyol":
                 getInformationTrendyolProducts(link);
                break;
            default: break;
        }
    }

    private void getInformationTrendyolProducts(String links) throws IOException {
        Gson gson = new Gson();
        WriteFile writeFile = new WriteFile();
        try {
            Elements elements = new UJsoup().getDiv(links).getElementsByTag("body");
            elements.forEach(element -> {
                element.getElementsByTag("script").dataNodes().forEach(dataNode -> {
                    String[] splited = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if(splited[0].isEmpty()){
                        if(splited[1].startsWith(" ")){

                        }else{
                            JSONObject jsonObject = new JSONObject(splited[1].trim().split("=" , 2)[1].split("window\\.TYPageName=")[0]);
                            Type type = new TypeToken<TrendyolModel>(){}.getType();
                            TrendyolModel trendyolModels = gson.fromJson(String.valueOf(jsonObject), type);
                            writeFile.writeAFile(trendyolModels.product);
                        }
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
