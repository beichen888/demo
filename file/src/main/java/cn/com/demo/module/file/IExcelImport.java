package cn.com.demo.module.file;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.common.FileMessageCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class IExcelImport {
    public List parseExcel(File excel) throws AppException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(excel);
            Workbook workbook = WorkbookFactory.create(inputStream);

            List list = new ArrayList<>();
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List sheetList = parseSheet(sheet, excel.getName());
                list.addAll(sheetList);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, excel.getName());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, excel.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, excel.getName());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List parseExcel(InputStream excel, String fileName) throws AppException {
        try {
            Workbook workbook = WorkbookFactory.create(excel);

            List list = new ArrayList<>();
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List sheetList = parseSheet(sheet, fileName);
                list.addAll(sheetList);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, fileName);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_READ_ERROR, fileName);
        }
    }

    protected abstract List parseSheet(Sheet sheet, String fileName) throws AppException;
}
