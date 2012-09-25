dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "vithesaurus"
    password = "vithesaurus"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://127.0.0.1:3306/vithesaurus?useUnicode=true&characterEncoding=utf-8"
        }
    }
    test {
        dataSource {
            dbCreate = "validate" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://127.0.0.1:3306/vithesaurus?useUnicode=true&characterEncoding=utf-8"
        }
    }
    production {
        dataSource {
            dbCreate = "validate"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}