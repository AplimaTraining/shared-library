def call(Map config) {
    print "Starting the Pipeline..."
    pipeline {
        echo "running build"
        echo "test"
        echo "${config.name} is beging passed..."
        echo "${config.status}"
    }
}
