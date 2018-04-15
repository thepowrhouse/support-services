
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
        gradle '-b config/build.gradle :config-server:build --stacktrace'
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
        gradle '-b config/build.gradle :discovery-server:build --stacktrace'
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
        gradle '-b config/build.gradle :edge-server:build --stacktrace'
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
        gradle '-b config/build.gradle :hystrix-turbine:build --stacktrace'
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
        gradle '-b config/build.gradle :auth-server:build --stacktrace'
    }
}


