package Commands;

import java.util.ArrayDeque;

interface ICommand {
    ArrayDeque<String> execute();
}

