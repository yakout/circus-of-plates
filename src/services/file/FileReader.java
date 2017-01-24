package services.file;

import models.data.ModelDataHolder;

/**
 * Created by Moham on 24-Jan-17.
 */
interface FileReader {

    public ModelDataHolder read(String path, String fileName);
    public String getExtension();
}
