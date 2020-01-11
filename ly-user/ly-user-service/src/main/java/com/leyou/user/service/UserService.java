package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate template;


    static final String KEY_PREFIX = "user:code:phone:";

    public Boolean check(String data, Integer type) {
        //1，用户名；2，手机；
        User user = new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
        }
        //0 1
        return userMapper.selectCount(user)!=1;//0!=1 true
    }

    public Boolean sendVerifyCode(String phone) {
        //产生验证码
        String s = NumberUtils.generateCode(5);//68691

        //把验证码放到redis
        template.opsForValue().set(KEY_PREFIX+phone,s,5, TimeUnit.MINUTES);
        //user:code:phone:18696196354 68691

        //发短信（略）
        //调用第三方接口 phone s
        return true;
    }


    public Boolean createUser(User user, String code) {
        //校验验证码
        String s = this.template.opsForValue().get(KEY_PREFIX + user.getPhone());
        //取值没有
        if(null==s){
           return false;
        }
        //取到数据，填错了
        if(!code.equals(s)){
            return  false;
        }

        //校验把用户名拿到数据查询，返回，实现。
        //插入数据库
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //加密
        String newpassword = CodecUtils.md5Hex(user.getPassword(), salt);
        //设置密码
        user.setPassword(newpassword);

        user.setCreated(new Date());

        boolean flag=this.userMapper.insertSelective(user)==1;
        if(flag){
            //redis数据删除
            this.template.delete(KEY_PREFIX + user.getPhone());

        }
        return  flag;


    }

    public User queryUser(String username, String password) {
        //查询用户
        User user = new User();
        user.setUsername(username);

        User user1 = this.userMapper.selectOne(user);
        //select * from tb_user where username='mike'
        //用户为空
        if(null==user1){
            return null;
        }
        //取出盐
        String salt = user1.getSalt();
        //页面传过来的密码进行加密
        String newpassword = CodecUtils.md5Hex(password, salt);
        //加密密码和数据库密码进行对比
        if(!user1.getPassword().equals(newpassword)){
            return null;
        }
        return  user1;

    }
}
