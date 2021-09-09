def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipeline {
     agent any
    stages {
    stage('Build') {
          steps {
            echo "Starting the build"
            runBuild(name: "build.sh")

          }
        }
    stage('Test') {
          steps {
            sh './jenkins/run-tests.sh'
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
        sh "./jenkins/deploy.sh ${pipelineParams.DEPLOY_TO}"
      }
    }
  }
 }
}
