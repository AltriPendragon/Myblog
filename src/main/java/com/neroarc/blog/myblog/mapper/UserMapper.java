package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author: fjx
 * @date: 2019/1/22 21:13
 * Descripe:
 */

@Mapper
@Repository
public interface UserMapper {


    /**
     * 根据id编号获得用户
     * @param id
     * @return
     */
    @Select("select *from user where id=#{id}")
    User getUserById(long id);

    /**
     * 根据name返回User
     * @param name 昵称
     * @return
     */
    @Select("select *from user where name=#{name}")
    User getUserByName(String name);

    /**
     * 根据providerId获得用户
     * @param id
     * @return
     */
    @Select("select *from user where provider_id=#{id}")
    User getUserByProviderId(String id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Insert("insert into user(password,name,gender,info,image_url,role,recent_login_date,provider_id) values(#{password},#{name},#{gender},#{info},#{imageUrl},#{role},#{recentLoginDate},#{providerId})")
    int addUser(User user);

    /**
     * 更新最近的登陆时间
     * @param user
     * @return
     */
    @Update("update user set recent_login_date = #{recentLoginDate} where provider_id=#{providerId}")
    int updateTime(User user);

    /**
     * 更新密码
     * @param user
     * @return
     */
    @Update("update user set password=#{password} where provider_id=#{providerId}")
    int updatePassword(User user);

    /**
     * 根据providerId判断用户是否存在
     * @param providerId
     * @return
     */
    @Select("select exists (select 1 from user where provider_id = #{providerId})")
    int isUserExist(String providerId);

    /**
     * 判断用户昵称是否存在
     * @param name
     * @return
     */
    @Select("select exists (select 1 from user where name = #{name})")
    int isUserNameExist(String name);

    /**
     * 得到头像
     * @param id
     * @return
     */
    @Select("select image_url from user where id=#{id}")
    String getHeadImg(long id);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Update("update user set name=#{name},gender=#{gender},info=#{info},image_url=#{imageUrl} where id=#{id}")
    int updateUserInfo(User user);

}
