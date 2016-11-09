package ar.edu.itba.pod.hz.client.reader;

import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.core.IMap;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class VotacionReader {
    // el directorio wc dentro en un directorio llamado "resources"
    // al mismo nivel que la carpeta src, etc.
    private static final String FILENAME = "files/dataset-1000.csv";

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[] {
            new ParseInt(new NotNull()), // tipoVivienda
            null,
            null,
            new ParseInt(new NotNull()), // edad
            new ParseInt(new NotNull()), // alfabetismo
            null,
            new NotNull(), // nombreDepto
            new NotNull(), // nombreProv
            new ParseInt(new NotNull()) // hogarId
        };
    }

    public static void readVotacion(final IMap<String, Citizen> theIMap) throws Exception {
        ICsvBeanReader beanReader = null;
        try {
            final InputStream is = VotacionReader.class.getClassLoader().getResourceAsStream(FILENAME);
            final Reader aReader = new InputStreamReader(is);
            beanReader = new CsvBeanReader(aReader, CsvPreference.STANDARD_PREFERENCE); // separador
                                                                                                  // ;

            // the header elements are used to map the values to the bean (names
            // must match)
//            final String[] header = beanReader.getHeader(true);
            beanReader.getHeader(true);

            final String[] header = new String[] {
                "tipoVivienda",
                null,
                null,
                "edad",
                "alfabetismo",
                null,
                "nombredepto",
                "nombreprov",
                "hogarid"
            };



            final CellProcessor[] processors = getProcessors();

            Citizen aV;
            while ((aV = beanReader.read(Citizen.class, header, processors)) != null) {
                System.out.println(String.format("lineNo=%s, rowNo=%s, customer=%s",
                        beanReader.getLineNumber(), beanReader.getRowNumber(), aV));
                theIMap.set(String.valueOf(beanReader.getLineNumber()), aV);
            }
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
    }
}
