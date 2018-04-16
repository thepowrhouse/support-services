
String basePath = 'Build Jobs'
String repo = 'thepowrhouse/support-services'

folder(basePath) {
    description 'Build Jobs for Support Service'
}

job("$basePath/config-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'cd config && ./gradlew :config-server:build --stacktrace'
    }
}

job("$basePath/discovery-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'cd config && ./gradlew :discovery-server:build --stacktrace'
    }
}

job("$basePath/edge-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'cd config && ./gradlew :edge-server:build --stacktrace'
    }
}

job("$basePath/hystrix-turbine-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'cd config && ./gradlew :hystrix-turbine:build --stacktrace'
    }
}

job("$basePath/auth-service-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell 'cd config && ./gradlew :auth-server:build --stacktrace'
    }
}


