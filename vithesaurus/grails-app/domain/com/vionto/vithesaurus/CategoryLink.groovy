/**
 * vithesaurus - web-based thesaurus management tool
 * Copyright (C) 2009 vionto GmbH, www.vionto.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 
package com.vionto.vithesaurus;

/**
 * A link from a synset to a category.
 */
class CategoryLink implements Comparable {

    Category category
    Synset synset
    
    static belongsTo = [Synset, Category]

    static mapping = {
        //id generator:'sequence', params:[sequence:'category_link_seq']
    }

    CategoryLink() {
    }
    
    CategoryLink(Synset fromSynset, Category category) {
        this.synset = fromSynset
        this.category = category
    }
    
    /**
     * Save and log to logging system (not to database).
     * Returns true if save was successful.
     */
    def saveAndLog() {
        def saved = save()
        if (saved) {
            log.info("linking synset '${synset.id}' to category ${category.id}")
        } else {
            log.info("could not link synset '${synset.id}' to category ${category.id}")
        }
        return saved
    }

    /** Sort by category name. */
    int compareTo(Object other) {
        return category.compareTo(other.category)
    }

    String toString() {
        return "${category}@${synset?.id}"
    }
}
