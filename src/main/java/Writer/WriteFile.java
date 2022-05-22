package Writer;

import Model.Trendyol.Product;

import java.io.File;
import java.io.FileWriter;

public class WriteFile {

    public void writeAFile(Product product){
        try{
            File file = new File("/Volumes/HDD/IdeaProjects/Read_Site/"+product.name);
            if(!file.exists()) {
                file.mkdirs();
            }else{
                System.out.println("Dosya var");
            }

            File file1 = new File(file , "sa.txt");
            FileWriter fileWriter = new FileWriter(file1);
            fileWriter.write("asdasd");
            fileWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
