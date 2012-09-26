package com.vionto.vithesaurus

import org.springframework.transaction.annotation.Transactional;

import com.vionto.vithesaurus.Synset;

class LOFileService {
    @Transactional
    boolean parseAndPersistFile(String filepath) {
        // Clear all synsets from db
        def synsetCount = Synset.count
        log.info "Will clear all synsets - count : $synsetCount"
        
        if(synsetCount > 0)
            Synset.list()*.delete(flush: true)

        // parse the file and save the new values
        parseFile(filepath)*.save();        
        return true
    }  
    
    List<Synset> parseFile(String filepath) {
        def termsSynsetsList = []
        def file = new File(filepath) 
        def termsSynsetsMap = [:]
        def lastBasicTerm 
        def language = Language.findByShortForm("el")
        
        log.info 'Start parsing the file...'
        
        // read file lines and fill termsSynsetsMap 
        file.eachLine { line, index ->
            def lineC = line.split("\\|")
            // basic term line
            if(!line.startsWith("-") && lineC.length > 1) {
                def word = lineC[0]
                Integer count = Integer.parseInt(lineC[1])
                if(!termsSynsetsMap.containsKey(word)) {
                    lastBasicTerm = word + "_1"
                    for(int i = 1 ; i <= count; i++) 
                        termsSynsetsMap.put(word+"_"+i, new Synset(new Term("word" : word, "language" : language)))
                }
            }
            // synset line
            else if(line.startsWith("-")){
                lineC.each { word -> if(!word.trim().equals("-") && !lastBasicTerm.startsWith(word)) termsSynsetsMap.get(lastBasicTerm).addTerm(new Term("word" : word, "language" : language)) }
                lastBasicTerm = lastBasicTerm.split("_")[0] +"_" +( Integer.parseInt(lastBasicTerm.split("_")[1]) + 1 )
            }
        }

        // Process termsSynsetsMap
        def synsetsIds = []
        def synsets = []
        
        log.info "File parsed ready to cleanup synset"
        
        termsSynsetsMap.each { basicTerm, synset -> 
            def synsetKey = synset.toString()
            
            if(!synsetsIds.contains(synsetKey)) {
                synsetsIds.add(synsetKey)
                synsets.add(synset)
            }
        }
        
        log.info "finally after cleanup found $synsets.size() synsets"

        synsets
    }
}