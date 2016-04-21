package pe.servosa.android.interfaces;

import java.io.File;

/**
 * Created by ucweb02 on 21/04/2016.
 */
public interface TaskExportarExcelDelegate {
    void taskOnInit();
    void taskOnCompletion(File file);
}
