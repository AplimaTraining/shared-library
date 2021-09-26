def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    print "Starting the Pipeline..."
    pipeline {
     agent any
    stages {
    stage('Build') {
          steps {
            echo "Starting the build"
            runBuild(name: "build.sh", arg: "${pipelineParams.lifecyclePhase}" )

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
        sh "./scripts/deploy.sh ${pipelineParams.deployTo}"
      }
    }
  }
 }
}
