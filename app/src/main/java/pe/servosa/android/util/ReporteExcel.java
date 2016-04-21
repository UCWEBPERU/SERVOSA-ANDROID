package pe.servosa.android.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import pe.servosa.android.model.PiramideAccidentabilidadEntity;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class ReporteExcel {

    private int positionInitWriteRows = 1;

//    private ArrayList<File> excelExportado = new ArrayList<>();
    private ArrayList<PiramideAccidentabilidadEntity> registros = new ArrayList<>();

    private File fileExcel;
    private WorkbookSettings wbSettings;
    private WritableWorkbook workbook;
    private WritableSheet sheet;

    private WritableFont cellFontHeader;
    private WritableFont cellFontRow;
    private WritableCellFormat cellFormatHeader;
    private WritableCellFormat rowFormatBlue;
    private WritableCellFormat rowFormatWhite;

    public ReporteExcel(ArrayList<PiramideAccidentabilidadEntity> registros) {
        this.registros = registros;
    }

    public void exportar() {
        if (registros.size() > 0) {
            fileExcel = createFileExcel();
            configExcel();
            addContentExcel();
            writeExcel();
        }
    }

    private void configExcel() {
        try {
            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("es", "ES"));

            workbook = Workbook.createWorkbook(fileExcel, wbSettings);

            sheet = workbook.createSheet("Datos de Accidentabilidad", 0);

            cellFontHeader = new WritableFont(WritableFont.ARIAL, 10);
            cellFontHeader.setBoldStyle(WritableFont.BOLD);
            cellFontHeader.setColour(Colour.WHITE);

            cellFontRow = new WritableFont(WritableFont.ARIAL, 10);
            cellFontRow.setBoldStyle(WritableFont.NO_BOLD);
            cellFontRow.setColour(Colour.BLACK);

            cellFormatHeader = new WritableCellFormat(cellFontHeader);
//            cellFormatHeader.setBackground(CustomColourExcel.ROW_HEADER);
            cellFormatHeader.setBackground(Colour.LIGHT_BLUE);
            cellFormatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormatHeader.setAlignment(Alignment.CENTRE);

            rowFormatBlue = new WritableCellFormat(cellFontRow);
//            cellFormatHeader.setBackground(CustomColourExcel.ROW_BLUE);
            rowFormatBlue.setBackground(Colour.LIGHT_TURQUOISE);
            rowFormatBlue.setBorder(Border.ALL, BorderLineStyle.THIN);
            rowFormatBlue.setAlignment(Alignment.CENTRE);

            rowFormatWhite = new WritableCellFormat(cellFontRow);
            rowFormatWhite.setBackground(Colour.WHITE);
            rowFormatWhite.setBorder(Border.ALL, BorderLineStyle.THIN);
            rowFormatWhite.setAlignment(Alignment.CENTRE);

            addHeaderExcel();

            sheet.setColumnView(0, 5);
            sheet.setColumnView(1, 10);
            sheet.setColumnView(2, 20);
            sheet.setColumnView(3, 20);
            sheet.setColumnView(4, 25);
            sheet.setColumnView(5, 10);
            sheet.setColumnView(6, 35);
            sheet.setColumnView(7, 20);
            sheet.setColumnView(8, 20);
            sheet.setColumnView(9, 20);
            sheet.setColumnView(10, 35);
            sheet.setColumnView(11, 50);
            sheet.setColumnView(12, 35);
            sheet.setColumnView(13, 80);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (WriteException ex) {
            ex.printStackTrace();
        }
    }

    private void addHeaderExcel() {
        try {
            sheet.addCell(new Label(0, 0, "N°", cellFormatHeader));
            sheet.addCell(new Label(1, 0, "REGION", cellFormatHeader));
            sheet.addCell(new Label(2, 0, "OPERACIÓN", cellFormatHeader));
            sheet.addCell(new Label(3, 0, "RUTA", cellFormatHeader));
            sheet.addCell(new Label(4, 0, "TRAMO", cellFormatHeader));
            sheet.addCell(new Label(5, 0, "PLACA", cellFormatHeader));
            sheet.addCell(new Label(6, 0, "OBSERVADOR", cellFormatHeader));
            sheet.addCell(new Label(7, 0, "NIVEL DE OBSERVADOR", cellFormatHeader));
            sheet.addCell(new Label(8, 0, "FECHA", cellFormatHeader));
            sheet.addCell(new Label(9, 0, "HORA", cellFormatHeader));
            sheet.addCell(new Label(10, 0, "EVENTO", cellFormatHeader));
            sheet.addCell(new Label(11, 0, "CATEGORIA", cellFormatHeader));
            sheet.addCell(new Label(12, 0, "TIPO", cellFormatHeader));
            sheet.addCell(new Label(13, 0, "DESCRIPCIÓN", cellFormatHeader));
        } catch (WriteException ex) {
            ex.printStackTrace();
        }
    }

    private void addContentExcel() {
        for (int i = 0; i < registros.size(); i++) {
            try {
                sheet.addCell(new Label(0, positionInitWriteRows, positionInitWriteRows + "", getFormatContentRow(i)));
                sheet.addCell(new Label(1, positionInitWriteRows, registros.get(i).getRegion(), getFormatContentRow(i)));
                sheet.addCell(new Label(2, positionInitWriteRows, registros.get(i).getOperacion(), getFormatContentRow(i)));
                sheet.addCell(new Label(3, positionInitWriteRows, registros.get(i).getRuta(), getFormatContentRow(i)));
                sheet.addCell(new Label(4, positionInitWriteRows, registros.get(i).getTramo(), getFormatContentRow(i)));
                sheet.addCell(new Label(5, positionInitWriteRows, registros.get(i).getPlaca(), getFormatContentRow(i)));
                sheet.addCell(new Label(6, positionInitWriteRows, registros.get(i).getObservador(), getFormatContentRow(i)));
                sheet.addCell(new Label(7, positionInitWriteRows, registros.get(i).getNivel_observador(), getFormatContentRow(i)));
                sheet.addCell(new Label(8, positionInitWriteRows, registros.get(i).getFecha(), getFormatContentRow(i)));
                sheet.addCell(new Label(9, positionInitWriteRows, registros.get(i).getHora(), getFormatContentRow(i)));
                sheet.addCell(new Label(10, positionInitWriteRows, registros.get(i).getEvento(), getFormatContentRow(i)));
                sheet.addCell(new Label(11, positionInitWriteRows, registros.get(i).getCategoria(), getFormatContentRow(i)));
                sheet.addCell(new Label(12, positionInitWriteRows, registros.get(i).getTipo(), getFormatContentRow(i)));
                sheet.addCell(new Label(13, positionInitWriteRows, registros.get(i).getDescripcion(), getFormatContentRow(i)));
            } catch (WriteException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            positionInitWriteRows++;
        }
    }

    private void writeExcel() {
        try {
            workbook.write();
            workbook.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (WriteException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }

    private WritableCellFormat getFormatContentRow(int currentPositionRow) {
        if (currentPositionRow % 2 == 1) {
            return rowFormatBlue;
        } else {
            return rowFormatWhite;
        }
    }

    private File getDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    private File createFileExcel() {

        String fileName = buildNameFileExcel();

        File directory = new File(getDirectory().getAbsolutePath() + "/Servosa/Reportes/");

        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        //file path
        return new File(directory, fileName);
    }

//    public ArrayList<File> getExcelsExportados() {
//        return excelsExportados;
//    }

    private String buildNameFileExcel() {
        Date date = new Date();

        String fecha;

        fecha = new SimpleDateFormat("dd-MM-yyyy").format(date);

        String fileName = "Reporte Datos de Accidentabilidad - " + fecha + ".xls";

        int numFileExist = numExistFileExcel(fileName);

        if (numFileExist != 0) {
            fileName = "Reporte Datos de Accidentabilidad - " + fecha + " (" + ++numFileExist + ").xls";
        }

        date = null;
        return fileName;
    }

    private int numExistFileExcel(String fileName) {
        File files = new File(getDirectory().getAbsolutePath() + "/Servosa/Reportes/");
        int count = 0;
        if (files.exists()) {
            File[] list = files.listFiles();

            for (File f : list) {
                String name = f.getName();
                if (name.equals(fileName) && name.endsWith(".xls"))
                    count++;
//                System.out.println("Contador " + count);
            }
        }

        files = null;
        return count;
    }

    public File getFileExcel() {
        return fileExcel;
    }
}
