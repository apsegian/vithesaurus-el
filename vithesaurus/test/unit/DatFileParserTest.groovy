import static org.junit.Assert.*;
import grails.test.mixin.Mock;

import org.junit.Test;
import org.vithesaurus.gr.DatFileParser;

import com.vionto.vithesaurus.Language;
import com.vionto.vithesaurus.Synset;

@Mock([Synset, Language])
class DatFileParserTest {

    @Test
    public void testParseFile() {
        println "Starting..."
        DatFileParser parser = new DatFileParser()
        def synsets = parser.parseFile("C:/TEMP/th_el_GR_v2.dat");
        
        synsets.each() { synset -> if(synset.terms.size() < 2) println "WARN: ${synset} -> ${synset.terms.size()}" };
    }

}
