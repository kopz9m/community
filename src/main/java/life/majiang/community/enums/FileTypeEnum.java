package life.majiang.community.enums;

public enum FileTypeEnum {
    
    AVATAR("avatars"),
    IMAGE("images");
    
    private String type;
    public String getType() {
        return type;
    }

    FileTypeEnum(String type){
        this.type = type;
    }
}
