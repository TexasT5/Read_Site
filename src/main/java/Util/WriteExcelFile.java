package Util;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dhatim.fastexcel.reader.ReadableWorkbook;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Stream;

public class WriteExcelFile implements Serializable{
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
                    Thread.sleep(1000);
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


    public Map<String , Object[]> readFastestExcelFile(File file){
        Map<String , Object[]> map = new TreeMap<>();
        AtomicInteger count = new AtomicInteger(0);
        try {
            var wb = new ReadableWorkbook(file);
            Stream<org.dhatim.fastexcel.reader.Row> row = wb.getFirstSheet().openStream();
            row.forEach(cells -> {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < cells.getCellCount() ; i++) {
                    org.dhatim.fastexcel.reader.Cell cell = cells.getCell(i);
                    String getValue = (String) cell.getValue();
                    list.add(getValue);
                }
                map.put(String.valueOf(count.get()) , list.toArray());
                count.getAndIncrement();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    public void fastestExcelLibrary(File file , String brandName , Map<String , Object[]> map){
        try(OutputStream outputStream = FileUtil.getOutputStream(file)){
            Workbook wb = new Workbook(outputStream, "Application", "1.0");
            Worksheet ws = wb.newWorksheet(brandName);
            Set<String> keyId = map.keySet();
            for (String key : keyId) {
                int cellId = 0;
                Object[] objectArr = map.get(key);
                if(objectArr != null){
                    for (Object ob: objectArr) {
                        ws.value(Integer.parseInt(key), cellId++ , ob.toString());
                    }
                }
            }
            wb.finish();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void writeFastestExcelFile(File file, String  getEnterUrl , Map<String , Object[]> map){
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sxssfSheet = workbook.createSheet(getEnterUrl);
        SXSSFRow row ;
        int rowId = 0;
        Set<String> keyId = map.keySet();
        try {
            for (String key : keyId) {
                row = sxssfSheet.createRow(rowId++);
                Object[] objectArr = map.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    SXSSFCell cell = row.createCell(cellid++);
                    Thread.sleep(100);
                    cell.setCellValue((String) obj);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            workbook.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String , Object[]> readExcelFile(File file){
        Map<String ,  Object[]> map = new TreeMap<>();
        int count = 0;
        try {
            FileInputStream excelFile = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = wb.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum() ; i++) {
                List<String> setList = new ArrayList<>();
                if(i == count){
                    XSSFRow row = sheet.getRow(i);
                    for (int j = row.getFirstCellNum(); j < row.getLastCellNum() ; j++) {
                        XSSFCell cell = row.getCell(j);
                        setList.add(cell.getStringCellValue());
                    }
                }
                if(!setList.isEmpty()) map.put(String.valueOf(count) , setList.toArray());
                Thread.sleep(1000);
                count++;
            }
            wb.close();
            //excelFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    public void writeExcelFileCustom(File file , int getCellSize ,Map<String , Object[]> map){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("31");
        XSSFRow row;
        Set<String> keyId = map.keySet();
        int rowId = 0;
        int column = 0;
        try {
            for (int i = 0; i < keyId.size(); i++) {
                for (Object object : map.get(String.valueOf(i))) {
                    row = spreadsheet.createRow(rowId++);
                    Cell cell = row.createCell(column++);
                    cell.setCellValue(object.toString());
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
