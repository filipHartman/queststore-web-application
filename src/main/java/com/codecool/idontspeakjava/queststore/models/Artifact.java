public class Artifact{

    enum ArtifactCategory{
        Basic, Magic;
    }
    
    private int id;
    private String title;
    private ArtifactCategory category;
    private String description;
    private int price;
}