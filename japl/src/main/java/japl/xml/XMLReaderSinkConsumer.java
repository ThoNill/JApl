package japl.xml;

import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;

import reactor.core.publisher.SynchronousSink;

class XMLReaderSinkConsumer implements
        Consumer<SynchronousSink<String[]>> {
    private XMLReader reader;

    XMLReaderSinkConsumer(XMLReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public void accept(SynchronousSink<String[]> client) {
        String[] nextLine;
        try {
            nextLine = reader.readNext();
            if (nextLine != null) {
                client.next(nextLine);
            } else {
                client.complete();
            }
        } catch (XMLStreamException e) {
            client.error(e);
        }
    }

}
