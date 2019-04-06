package japl.xml;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import japl.basis.AplRuntimeException;

/**
 * The BasisReader is a baseclass for reading XML Files with a StaxReader
 * 
 * @author Thomas Nill
 *
 */


abstract class BasisReader implements Closeable{

    protected XMLStreamReader xmlr;

    /**
     * Constructor for super class
     */
    protected BasisReader() {
        super();
    }
    /**
     * start to read a file
     * 
     * @param filename
     *            (name of the file)
     */
   
    void read(String filename) {
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        try {
            xmlr = factory.createXMLStreamReader(filename, new FileInputStream(
                    filename));
        } catch (FileNotFoundException | XMLStreamException e) {
            throw new AplRuntimeException("Runtime.FILE_PROCESSING",e);
        }
    }

    protected void next(XMLStreamReader xmlr) {
        switch (xmlr.getEventType()) {
        case XMLStreamConstants.START_ELEMENT:
            nextStartElement(xmlr);
            break;
        case XMLStreamConstants.END_ELEMENT:
            nextEndElement(xmlr);
            break;
        case XMLStreamConstants.SPACE:
        case XMLStreamConstants.CHARACTERS:
            nextText(xmlr);
            break;
        case XMLStreamConstants.PROCESSING_INSTRUCTION:
        case XMLStreamConstants.CDATA:
        case XMLStreamConstants.COMMENT:
        case XMLStreamConstants.ENTITY_REFERENCE:
        case XMLStreamConstants.START_DOCUMENT:
        default:
            break;
        }

    }
    
    protected abstract void nextText(XMLStreamReader xmlr);

    protected abstract void nextEndElement(XMLStreamReader xmlr);

    protected abstract void nextStartElement(XMLStreamReader xmlr);


}