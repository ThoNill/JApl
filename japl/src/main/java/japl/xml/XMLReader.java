package japl.xml;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import japl.basis.AplRuntimeException;

/**
 * Helper to generate a interface with string constants of the different paths
 * in a xml file.
 * 
 * @author Thomas Nill
 *
 */
class XMLReader extends BasisReader {
    private String filename;
    private Deque<QName> path = new ArrayDeque<>();
    private String resultPathString;
    private String text;
    private String art;
    private int countAttributes = 0;
    private boolean readNext = false;

    /**
     * Constructor of an uninitialized Reader
     * 
     */
    XMLReader(String filename) {
        super();
        this.filename = filename;
    }
    
    private String toPathString(Deque<QName> path) {
        StringBuilder builder = new StringBuilder();
        for(QName q : path) {
            builder.append("/");
            builder.append(q.toString());
        }
        return builder.toString();
        
    }

    @Override
    protected void nextText(XMLStreamReader xmlr) {
        int start = xmlr.getTextStart();
        int length = xmlr.getTextLength();
        art = "TEXT";
        text = new String(xmlr.getTextCharacters(), start, length);
        countAttributes=0;
        resultPathString = toPathString(path);
    }

    @Override
    protected void nextEndElement(XMLStreamReader xmlr) {
        if (xmlr.hasName()) {
            resultPathString = toPathString(path);
            path.pop();
            art = "END";
            text = "";
        }
        countAttributes=0;
    }

    @Override
    protected void nextStartElement(XMLStreamReader xmlr) {
        if (xmlr.hasName()) {
            path.push(xmlr.getName());
            art = "START";
            text = "";
            resultPathString = toPathString(path);
        }
        countAttributes = xmlr.getAttributeCount();

    }

   
    private void processAttribute(XMLStreamReader xmlr, int index) {
        if (xmlr.getAttributeCount() <= index) {
            throw new AplRuntimeException("Count <= index error");
        }
        String localName = xmlr.getAttributeLocalName(index);
        text = xmlr.getAttributeValue(index);
        path.push(new QName("@" + localName));
        resultPathString = toPathString(path);
        art = "ATTRIBUT";
        path.pop();
    }

    String[] readNext() throws XMLStreamException {
        if (xmlr == null) {
            read(filename);
        }
        if (countAttributes > 0) {
            countAttributes--;
            processAttribute(xmlr, countAttributes);
            return generatePayload();
        } else {
            if (readNext) {
                xmlr.next();
            }
            if (xmlr.hasNext()) {
                next(xmlr);
                readNext = true;
                return generatePayload();
            }
            return null;
        }

    }

    private String[] generatePayload() {
        return new String[] { resultPathString, art, text };
    }

    @Override
    public void close() {
        if (xmlr != null) {
            try {
                xmlr.close();
            } catch (XMLStreamException e) {
                throw new AplRuntimeException("can not close " + filename,e);
            }
        }
        xmlr = null;
    }

}
