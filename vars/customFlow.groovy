def call(Map config) {
    print "Starting the Pipeline..."
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo "Starting the build"
                    runBuild(name: "build.sh", arg: "${config.lifecyclePhase}" )
                }
            }
            stage('Test') {
                steps {
                    sh './scripts/run-tests.sh'
                }
            }
            stage('Confirm Deploy') {
                when {
                    branch 'main'
                }
                steps {
                    timeout(time: 3, unit: 'MINUTES' ) {
                    input(message: "Okay to Deploy to Staging?", ok: "Let's Do it!")
                    }
                }
            }
            stage('Deploy') {
                when {
                    branch 'main'
                }
                steps {
                    sh "./scripts/deploy.sh ${config.deployTo}"
                }
            }
        }
    }
}
