def call(Map config) {
    sh "echo Hello DevOps world, ${config.name}"
}
