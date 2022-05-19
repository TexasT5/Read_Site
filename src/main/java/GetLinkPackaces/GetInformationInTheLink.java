package GetLinkPackaces;

import Model.TrendyolModel;
import Util.UJsoup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
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
        File file = new File("D:\\BackEndDevelopment\\Read_Site\\sa.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        AtomicBoolean isFinish = new AtomicBoolean(false);
        Gson gson = new Gson();
        try {
            if(!file.exists()){
                file.createNewFile();
            }
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
                            trendyolModels.product.variants.forEach(variants -> {
                                System.out.println(variants.barcode);
                            });




                            //System.out.println(s);
                        }
                        //System.out.println(splited[1]);
                    }
                });
            });

            if(isFinish.get()){
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T>JSONObject getJsonObject(T getJson){
        return new JSONObject(getJson);
    }
}
