package GetLinkPackaces;

import Util.UJsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.File;
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
                while(threadEnding.get()){
                    try {
                        Document document = uJsoup.getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i);
                        Elements getLink = document.select(".p-card-chldrn-cntnr > a");
                        if(getLink.isEmpty()){
                            System.out.println("boş");
                            threadEnding.set(false);
                            executorService.shutdown();
                            try {
                                if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                                    executorService.shutdownNow();
                                }
                            } catch (InterruptedException e) {
                                executorService.shutdownNow();
                            }
                        }else{
                            getLink.forEach(element -> {
                                String getHref = element.attr("href");
                                String[] getBrandSplit = getHref.split(getBrand);
                                if(getBrandSplit[0].equals("/")){
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    listModel.addElement("https://www.trendyol.com"+getHref);
                                    getInformationInTheLink.getInformationTrendyolProducts("https://www.trendyol.com/"+getHref , getSelectedFile);
                                    JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                                    scrollBar.setValue(scrollBar.getMaximum());
                                }
                            });
                        }
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
