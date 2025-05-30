# Jaego

**Jaego** (재고) means "stock" or "inventory" in Korean. It is a simple Java Swing program to help manage product inventories.

## Requirements
- Jaego requires **JDK 17 or higher** to run.
- Building from source also requires Maven.

## Features

- Add new products with ID, name, price, quantity, and category.
- Built-in input validation during entry save.
- Organized product list with table view.

## Build and Run

1. **Clone the repository:**

    ```sh
    git clone https://github.com/nozomi-75/Jaego.git
    cd Jaego
    ```

2. **Build the project and create the JAR with dependencies using Maven:**

    ```sh
    mvn clean package
    ```

    The JAR file with all dependencies will be generated at `target/Jaego-1.1-SNAPSHOT-jar-with-dependencies.jar`.

3. **Run the generated JAR:**

    ```sh
    java -jar target/Jaego-1.1-SNAPSHOT-jar-with-dependencies.jar
    ```

## Future enhancements

- Edit and delete entries
- Category filtering and search
- Summary statistics
- Improved UI themes

## Credits

- [FlatLaf](https://www.formdev.com/flatlaf/) is used for the look and feel of the program.

## License

MIT License. See [LICENSE](LICENSE) for details.

## Made with ❤️ by Zens

- Bryan Suela
- Jan Conrad Maniquiz
- Marco Jaezzy Bacolto
- Sebastian Abad
- Keiaa (nozomi-75)
