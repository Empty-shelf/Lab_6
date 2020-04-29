package Commands;

import java.util.ArrayList;

public class FilterContainsName extends ACommand {
    String arg;
    private ArrayList<String> answer;

    public FilterContainsName(String name, String arg){
        this.name = name;
        this.arg = arg;
    }

    public void setAnswer(ArrayList<String> answer){
        this.answer = answer;
    }

    @Override
    public String execute() {
        manager.filter_contains_name(arg);
        return null;
    }
}
