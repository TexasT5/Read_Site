package GetLinkPackaces;

import Model.Trendyol.Product;
import Model.Trendyol.TrendyolModel;
import Util.UJsoup;
import Writer.WriteFile;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class GetInformationInTheLink {
    public void getInformationTrendyolProducts(String links , JFileChooser getSelectedFile) throws IOException {
        try {
            Elements elements = new UJsoup().getDiv(links).getElementsByTag("body");
            elements.forEach(element -> {
                element.getElementsByTag("script").dataNodes().forEach(dataNode -> {
                    String[] splited = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if(splited[0].isEmpty()){
                        JSONObject jsonObject = new JSONObject(splited[1].trim().split("=" , 2)[1].trim().split("window\\.TYPageName=")[0]);
                        writeAFileFuncPushed(getSelectedFile , jsonObject);
                    }
                });


                element.getElementsByTag("a").forEach(element1 -> {
                    System.out.println(element1.html());
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAFileFuncPushed(JFileChooser getSelectedFile , JSONObject jsonObject){
        Gson gson = new Gson();
        WriteFile writeFile = new WriteFile();
        Type type = new TypeToken<TrendyolModel>(){}.getType();
        TrendyolModel trendyolModel = gson.fromJson(String.valueOf(jsonObject), type);
        writeFile.writeAFile(trendyolModel.product , getSelectedFile);
        System.out.println(trendyolModel.product.name);
    }
}
