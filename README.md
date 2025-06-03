# Jaego

**Jaego** (재고) means "stock" or "inventory" in Korean. It is a simple Java Swing program to help manage product inventories.

## Requirements
- Jaego requires **JDK 17 or higher** to run.
- Building from source also requires Maven and Git.

## Features

- Add new products with ID, name, price, quantity, and category.
- Edit existing products **via double-click** with a user-friendly dialog.
- Delete items from the list within the edit view.
- Built-in input validation during entry save.
- Organized product list with table view.
- Save and load product data to/from an **`inventory.csv`** file.

## Running

1. [Download the latest Jaego JAR from the Releases page](https://github.com/nozomi-75/Jaego/releases).
2. Run the application:

    ```sh
    java -jar Jaego.jar
    ```

    Alternatively, you may mark the file as executable and run it as a program.

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

- Category filtering and search for faster item lookup
- Summary statistics (e.g. total inventory value, item counts by category)
- Improved UI and navigation (keyboard shortcuts, better layout, theming)
- Import products from CSV to complement existing export functionality

## Credits

- [FlatLaf](https://www.formdev.com/flatlaf/) is used for the look and feel of the program.
- [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/) is used for reading and writing `inventory.csv`.

## License

MIT License. See [LICENSE](LICENSE) for details.

## Made with ❤️ by Zens

- Bryan Suela
- Jan Conrad Maniquiz
- Marco Jaezzy Bacolto
- Sebastian Abad
- Keiaa (nozomi-75)
