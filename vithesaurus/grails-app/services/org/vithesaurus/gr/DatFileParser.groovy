package org.vithesaurus.gr

import com.vionto.vithesaurus.*;

import com.vionto.vithesaurus.Synset;

class DatFileParser {
    List<Synset> parseFile(String filepath) {
        def termsSynsetsList = []
        def file = new File(filepath) 
        def termsSynsetsMap = [:]
        def tmpTerm 
        def language = new Language("el", "el")
        
        println 'Start parsing the file...'
        
        // read file lines and fill termsSynsetsMap 
        file.eachLine { line, index ->
            // basic term line
            if(!line.startsWith("-") && line.split("\\|").length > 1) {
                def word = line.split("\\|")[0]
                Integer count = Integer.parseInt(line.split("\\|")[1])
                if(!termsSynsetsMap.containsKey(word)) {
                    Term term = new Term("word" : word, "language" : language)
                    
                    for(int i = 1 ; i <= count; i++) {
                        def ss = new Synset()
                        ss.addTerm(term)
                        termsSynsetsMap.put(word+"_"+i, ss)
                    }
                    tmpTerm = word+"_1"
                }
            }
            else if(line.startsWith("-")){
                line.split("\\|").each { word -> if(!word.trim().equals("-") && !tmpTerm.startsWith(word)) termsSynsetsMap.get(tmpTerm).addTerm(new Term("word" : word, "language" : language)) }
                tmpTerm = tmpTerm.split("_")[0] +"_" +( Integer.parseInt(tmpTerm.split("_")[1]) + 1 )
            }
        }

        // Process termsSynsetsMap
        def synsetsIds = []
        def synsets = []
        
        println "File parsed ready to cleanup synset"
        
        termsSynsetsMap.each { basicTerm, synset -> 
            def synsetKey = synset.toString()
            
            if(!synsetsIds.contains(synsetKey)) {
                synsetsIds.add(synsetKey)
                synsets.add(synset)
            }
        }
        
        println "finally after cleanup found $synsets.size() synsets"

        synsets
    }
}