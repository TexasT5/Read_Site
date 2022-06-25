package Util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class WriteExcelFile {
    public void writeExcelFile(File file  , String  getEnterUrl , Map<String , Object[]> map){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(getEnterUrl);
        XSSFRow row;
        Set<String> keyId = map.keySet();
        int rowId = 0;
        try {
            for (String key : keyId) {
                row = spreadsheet.createRow(rowId++);
                Object[] objectArr = map.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String , List<String>> readExcelFile(File file){
        Map<String , List<String>> map = new TreeMap<>();
        try {
            FileInputStream excelFile = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = wb.getSheetAt(0);
            List<String> getCellText = new ArrayList<>();

            int rowCount = 0;
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum()  ; i++) {
                Row row = sheet.getRow(i);
                if(row != null){
                    for (int j = row.getFirstCellNum(); j < row.getLastCellNum() ; j++) {
                        Cell cell = row.getCell(j);
                        if(!cell.getStringCellValue().isEmpty()){
                            getCellText.add(cell.getStringCellValue());
                        }
                    }

                }
                if(!getCellText.isEmpty()){
                    map.put(String.valueOf(rowCount),  getCellText);
                }
                rowCount++;
            }
            wb.close();
            excelFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    public int getExcelFileColumnSize(File file){
        int size = 0;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum() ; i++) {
                if(sheet.getRow(i) != null){
                    size = sheet.getRow(i).getLastCellNum();
                }
            }
            fileInputStream.close();
            wb.close();
        }catch (Exception e){e.printStackTrace();}
        return size;
    }


    public int getExcelFileRowSize(File file){
        int size = 0;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            size = sheet.getLastRowNum();
            fileInputStream.close();
            wb.close();
        }catch (Exception e){e.printStackTrace();}
        return size;
    }

    public void writeExcelFileCustom(File file , int getCellSize ,Map<String , List<String>> map){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("31");
        XSSFRow row;
        Set<String> keyId = map.keySet();
        int rowId = 0;
        int column = 0;
        try {
            for (int i = 0; i < keyId.size(); i++) {
                row = spreadsheet.createRow(i);
                List<String> stringList = map.get(String.valueOf(i));
                for (int j = 0; j < getCellSize ; j++) {
                    Cell cell = row.createCell(j);
                    for (String writeCell : stringList) {
                        cell.setCellValue(writeCell);
                    }
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
