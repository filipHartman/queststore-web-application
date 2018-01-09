public class Quest{

    enum QuestCategory{
        Basic, Extra;
    }
    
    private int id;
    private String title;
    private QuestCategory category;
    private String description;
    private int reward;
}