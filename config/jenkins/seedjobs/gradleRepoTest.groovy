String basePath = 'example1'
String repo = 'thepowrhouse/support-services'

folder(basePath) {
    description 'Auth Service'
}

job("$basePath/auth-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        gradle 'assemble'
    }
}

job("$basePath/auth-service-deploy") {
    parameters {
        stringParam 'host'
    }
    steps {
        shell 'scp war file; restart...'
    }
}
