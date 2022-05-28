package GetLinkPackaces;

import Util.UJsoup;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
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
        AtomicBoolean threadEnding = new AtomicBoolean(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while(threadEnding.get()){
                    try{
                        new UJsoup().getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i)
                                .select(".p-card-chldrn-cntnr > a")
                                .forEach(element -> {
                                    String getHref = element.attr("href");
                                    String[] getBrandSplit = getHref.split(getBrand);
                                    if(getHref.isEmpty()){
                                        threadEnding.set(false);
                                    }else{
                                        if(getBrandSplit[0].equals("/")){
                                            System.out.println("çalışıyor");
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
                                    }
                                });
                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                    i++;
                }
            }
        });
        thread.start();
        System.out.println(thread.isAlive());
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
