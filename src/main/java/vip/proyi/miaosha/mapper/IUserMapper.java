package vip.proyi.miaosha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vip.proyi.miaosha.entity.User;

@Mapper
public interface IUserMapper extends BaseMapper<User> {
    @Select("select * from user where name=#{name}")
    User getByName(@Param("name") String name);
}
