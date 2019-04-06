package japl.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class FromSaxToStax extends DefaultHandler implements XMLEventReader {
    
    private BlockingQueue<XMLEvent> queue = new ArrayBlockingQueue<>(100);
    private XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    private boolean open = true;

    @Override
    public Object next() {
        if(hasNext()) {
            throw new NoSuchElementException(); 
        }
        return take();
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        return take();
    }

    @Override
    public boolean hasNext() {
        return queue.isEmpty() || open;
    }

    @Override
    public XMLEvent peek() throws XMLStreamException {
        return queue.peek();
    }

    @Override
    public String getElementText() throws XMLStreamException {
        XMLEvent ev = peek();
        if (ev != null && (ev.isStartElement() && ev.isCharacters())) {
            return ev.asCharacters().getData();
        }
        return "";
    }

    @Override
    public XMLEvent nextTag() throws XMLStreamException {
        XMLEvent ev = take();
        while (ev != null && !(ev.isStartElement() || ev.isEndElement())) {
            ev = take();
        }
        return ev;
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public void close() throws XMLStreamException {
        queue.clear();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        put(eventFactory.createCharacters(new String(ch, start, length)));
    }

    @Override
    public void endDocument() {
        put(eventFactory.createEndDocument());
        open = false;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        put(eventFactory.createEndElement(qName, uri, localName));
    }

    @Override
    public void startDocument() {
        put(eventFactory.createStartDocument());
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) {
        put(eventFactory.createStartElement(qName, uri, localName,
                createList(attributes).iterator(),
                (new ArrayList<Namespace>()).iterator()));
    }

    private List<Attribute> createList(Attributes attributes) {
        List<Attribute> list = new ArrayList<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            list.add(eventFactory.createAttribute(attributes.getQName(i),
                    attributes.getValue(i)));
        }
        return list;
    }
    

    private void put(XMLEvent o) {
        try {
            queue.put(o);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private XMLEvent take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

}
