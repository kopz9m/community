package life.majiang.community.dto;

import lombok.Data;

@Data
public class EmailUserDto {
    private String name;
    private String email;
    private String password;
    private String verificationCode;
}
