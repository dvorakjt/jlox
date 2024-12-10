# JLOX

I'm following along with Robert Nystrom's book, 
[Crafting Interpreters](https://craftinginterpreters.com/).
This repository contains the code for the first interpreter as listed in the book. 
I'm using this repo to keep track of my own progress.

### Compiling the Interpreter

To compile the interpreter, in a terminal, navigate into the root directory and run the
command:

```
javac com/craftinginterpreters/lox/Lox.java
```

### Executing Lox Code

To start an interactive Lox interpreter, you can run:

```
java com.craftinginterpreters.lox.Lox
```

Alternatively, you can execute a lox file by providing the path to the file as an
argument to the above command:

```
java com.craftinginterpreters.lox.Lox <path/to/myprogram.lox>
```

Currently, the interpreter simply lists the tokens it detects.