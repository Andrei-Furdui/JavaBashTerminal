# JavaBashTerminal

A Java application designed to be used in Windows (for the moment), its purpose being to emulate (simulate) some of the basic Linux commands 
(some of them being customized for a more-friendly usage), such as {ls, time, pwd, cat etc.}.

## Installation
1. Clone this repo.
2. Go into the JBashTerminal directory and build the project using Maven (e.g. mvn install).
3. Call the jar for the specific part of this application by also specifying the executable class, as follows (pay attention that these commands have some placeholders):
- java -cp ./Windows/target/Windows-[version].jar com.jbashterminal.windows.JTerminal.StartTerminal
- java -cp ./Windows/target/Windows-[version].jar com.jbashterminal.windows.JFrame.StartFrame

    [version] to be replaced with the actual version of the application - can be found using the pom.xml file. For example:
    ```maven
      <parent>
            ...
            <version>1.0-SNAPSHOT</version>
        </parent>
    ```

    So the full command would be: java -cp ./Windows/target/Windows-1.0-SNAPSHOT.jar com.jbashterminal.windows.JTerminal.StartTerminal

4. Once the terminal is started, type help to get the list of supported commands and their meaning.


## Project status
(This section is updated periodically)


The project is currently under development so make sure to get the latest version each time.

## What's next?
The plan is to make this project flexible enough such that it can be used in any Operating System, covering as many Linux commands as possible.
