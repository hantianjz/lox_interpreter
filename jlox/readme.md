# Run generate AST
`mvn exec:java@generate_ast -Dexec.args="src/main/java/com/hjz/lox/"`

# Run AST printer test
`mvn exec:java@ast_printer`

# Compile project
`mvn compile`

# Create jar package
`mvn package`

# Running unit test
`mvn test`
