def call(Map config = [:]) {
  def contents = libraryResource "scripts/${config.name}"
  writeFile file: "${config.name}", text: contents
  sh """
    chmod a+x ./${config.name}
    ./${config.name}
  """
}
