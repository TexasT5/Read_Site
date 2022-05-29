package GetLinkPackaces;

import Util.UJsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetLinks {
    GetInformationInTheLink getInformationInTheLink = new GetInformationInTheLink();
    public void getLinkInSites(String siteName, String enterBrand, DefaultListModel<String> listModel, JScrollPane main_scroll_pane , JFileChooser getSelectFile){
        switch (siteName){
            case "Trendyol":
                getTrendyolProductLinks(enterBrand , listModel, main_scroll_pane , getSelectFile);
            break;

            default:break;
        }
    }

    private void getTrendyolProductLinks(String getBrand, DefaultListModel<String> listModel, JScrollPane scrollPane , JFileChooser getSelectedFile){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UJsoup uJsoup = new UJsoup();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                AtomicBoolean threadEnding = new AtomicBoolean(true);
                int i = 1;
                if(threadEnding.get()){
                    try {
                        Document document = uJsoup.getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i);
                        document.getElementsByClass(".no-rslt-text").forEach(element -> {
                            System.out.println(element.attr("span"));
                        });


                        document.select(".p-card-chldrn-cntnr > a")
                        .forEach(element -> {
                            String getHref = element.attr("href");
                            String[] getBrandSplit = getHref.split(getBrand);
                            if(getBrandSplit[0].equals("/")){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                listModel.addElement("https://www.trendyol.com"+getHref);
                                try {
                                    getInformationInTheLink.getInformationTrendyolProducts("https://www.trendyol.com"+getHref , getSelectedFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                JScrollBar scrollBar =  scrollPane.getVerticalScrollBar();
                                scrollBar.setValue(scrollBar.getMaximum());
                            }
                        });
                        System.out.println("Thread start i : " + i);
                        executorService.shutdown();
                        try {
                            if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                                executorService.shutdownNow();
                            }
                        } catch (InterruptedException e) {
                            executorService.shutdownNow();
                        }

                        System.out.println("Thread end i : " + i);
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /* private void getURLS(String link){
        for(int i = 1 ; i <= 9999; i++){
            try{
                var jsoupGet = Jsoup.connect("https://www.trendyol.com/sr?q=kom&qt=kom&st=kom&os=1&pi="+i).get()
                        .select(".p-card-chldrn-cntnr > a");

                jsoupGet.forEach(element -> {
                    String getHref = element.attr("href");
                    String[] getHrefSplite = getHref.split("kom");
                    if(getHrefSplite[0].equals("/")){
                        System.out.println(getHref);
                    }

                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
