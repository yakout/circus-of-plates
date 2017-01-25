package services.file;

import models.data.ModelDataHolder;

/**
 * Created by Moham on 24-Jan-17.
 */
interface FileWriter {

    public void write(ModelDataHolder dataHolder, String path, String
            fileName);

    public String getExtension();
}
