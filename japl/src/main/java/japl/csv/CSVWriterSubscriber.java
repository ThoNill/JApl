package japl.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.opencsv.CSVWriter;

class CSVWriterSubscriber implements Consumer<Object[]>, Runnable {
    private static Logger logger = Logger.getLogger(CSVWriterSubscriber.class);
    
    private String filename;
    private CSVWriter writer;

    CSVWriterSubscriber(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void accept(Object[] nextLine) {
        try {
            String[] snextLine = new String[nextLine.length];
            for(int i =0;i< nextLine.length;i++) {
                Object o = nextLine[i];
                snextLine[i] = (o == null) ? null : o.toString();
            }
            getWriter().writeNext(snextLine);
        } catch (IOException e) {
            logger.error(e);
        }

    }
    

    @Override
    public void run() {
        try {
            getWriter().close();
        } catch (IOException e) {
            logger.error(e);
        }

    }
    
    
    public void close() {
        try {
            getWriter().close();
        } catch (IOException e) {
            logger.error(e);
        }
    }


    private CSVWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new CSVWriter(new FileWriter(filename), ';');
        }
        return writer;
    }
}
