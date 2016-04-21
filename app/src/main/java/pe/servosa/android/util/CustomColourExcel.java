package pe.servosa.android.util;

import jxl.format.Colour;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class CustomColourExcel extends Colour {
    public static final CustomColourExcel ROW_HEADER = new CustomColourExcel(65, "ROW HEADER", 79, 129, 189);
    public static final CustomColourExcel ROW_BLUE = new CustomColourExcel(66, "ROW BLUE", 220, 230, 241);

    protected CustomColourExcel(int val, String s, int r, int g, int b) {
        super(val, s, r, g, b);
    }



}
