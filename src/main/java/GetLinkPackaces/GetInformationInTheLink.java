package GetLinkPackaces;

import Model.Trendyol.TrendyolModel;
import Util.UJsoup;
import Writer.WriteFile;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLEmbedElement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.List;


public class GetInformationInTheLink {
    public void getInformationTrendyolProducts(String links , JFileChooser getSelectedFile){
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
            });
            htmlUnitTests();

        } catch (IOException e ) {

        }
    }

    private void writeAFileFuncPushed(JFileChooser getSelectedFile , JSONObject jsonObject){
        Gson gson = new Gson();
        WriteFile writeFile = new WriteFile();
        Type type = new TypeToken<TrendyolModel>(){}.getType();
        TrendyolModel trendyolModel = gson.fromJson(String.valueOf(jsonObject), type);
        writeFile.writeAFile(trendyolModel.product , getSelectedFile);
    }


    private void htmlUnitTests(){
        try (final WebClient webClient = new WebClient()) {

            String url = "https://www.trendyol.com/kom/kadin-barbara-toparlayici-balensiz-minimizer-sutyen-p-51579114?boutiqueId=607370&merchantId=257173";
            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(10_000);
            webClient.getOptions().setCssEnabled(false);
            System.out.println();
            page.querySelectorAll("div.slicing-attributes").forEach(domNode -> System.out.println(domNode.asXml()));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
