package life.majiang.community.service;

import life.majiang.community.dto.EmailUserDto;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import life.majiang.community.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class MailService {

    @Value("${myHost}")
    private String host;
    @Value("${server.port}")
    private String port;
    @Autowired
    private UserMapper userMapper;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    // 给前端输入的邮箱，发送验证码
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证您的邮箱");//主题
            //生成验证码并发送
            String code = FileUtils.randomCode();
            session.setAttribute("email", email);
            session.setAttribute("codeSent", code);
            mailMessage.setText("您的验证码是：" + code);
            mailMessage.setTo(email);
            mailMessage.setFrom(from);
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 检验验证码是否一致
     */
    public boolean loginByCode(EmailUserDto emailUserDto, HttpSession session, HttpServletResponse response) {
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String codeSent = (String) session.getAttribute("codeSent");

        String codeToCheck = emailUserDto.getVerificationCode();

        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()) {
            //return "error,请重新注册";
            return false;
        } else if (!codeSent.equals(codeToCheck)) {
            //return "error,请重新注册";
            return false;
        }

        System.out.println("验证成功");

        //保存数据
        User user = new User();
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setEmail(email);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setName("用户_" + System.currentTimeMillis());
        user.setAvatarUrl("http://" + host + ":" + port + "/file/get/?type=avatars&filename="
                + FileUtils.getDefaultAvatar());
        userMapper.insert(user);
        response.addCookie(new Cookie("token",token));
        System.out.println("添加用户成功");
        return true;
    }

    // 密码校验，成功则加入 cookie
    public boolean loginInByPass(String email, String password, HttpServletResponse response) {
        // 根据邮箱查找用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email.trim());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
            System.out.println("用户不存在！");
            return false;
        }

        if (users.size() > 1) {
            System.out.println("用户重复！");
            return false;
        }

        // 比较密码
        if (password.trim().equals(users.get(0).getPassword().trim())) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            // 更新 cookie
            user.setToken(token);
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(user,userExample);
            response.addCookie(new Cookie("token",token));
            return true;
        }
        System.out.println("密码错误!");
        return false;
    }
}
