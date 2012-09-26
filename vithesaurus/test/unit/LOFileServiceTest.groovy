import static org.junit.Assert.*
import grails.test.mixin.Mock
import grails.test.mixin.TestFor;

import com.vionto.vithesaurus.LOFileService
import com.vionto.vithesaurus.Language
import com.vionto.vithesaurus.Synset
import com.vionto.vithesaurus.Term;

@Mock([Synset, Language, Term])
@TestFor(LOFileService)
class LOFileServiceTest {
    
    LOFileService service
    static def mockSynsets = []
    
    void setUp() {
       println "In setUp" 
       service = new LOFileService()
       def mockLanguages = [new Language(longForm: "el", shortForm : "el")]
       mockDomain(Language, mockLanguages)
       mockDomain(Synset, mockSynsets)
    }
    
    def testParseFile() {
        service.parseFile("../lo-files/th_el_GR_v2.dat").each() { 
            synset -> 
                mockSynsets.add synset
                if(synset.terms.size() < 2) println "WARN: ${synset} -> ${synset.terms.size()}" }
        assert true
    }

    def testParseAndPersistFile() {
        def count = Synset.count()
        assert service.parseAndPersistFile("../lo-files/th_el_GR_v2.dat")
        assert count == Synset.count()
        println "Synsets saved : $Synset.count()"
        println "Terms saved : $Term.count()"
    }
}
