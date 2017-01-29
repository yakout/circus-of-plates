package services.file;

import models.data.ModelDataHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Ahmed Khaled on 28/01/2017.
 */
public class XmlWriter implements FileWriter {
    private Document doc;
    private Element root;
    public XmlWriter() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            root = doc.createElement("Game");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    private static Logger logger = LogManager.getLogger(XmlWriter.class);
    @Override
    public void write(ModelDataHolder dataHolder, String path, String fileName) {

    }

    @Override
    public String getExtension() {
        return null;
    }
}
