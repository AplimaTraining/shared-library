def call(Map config) {
    print "Starting the Pipeline..."
    node {
        echo "running build"
        echo "${config.name)"
    }
}
