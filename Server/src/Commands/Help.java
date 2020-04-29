package Commands;

public class Help extends ACommand {

    private String [] answer;

    Help(String name){
        this.name = name;
    }

    void setAnswer(String[] answer){
        this.answer = answer;
    }

    @Override
    public String execute() {
        manager.help();
        return null;
    }
}
