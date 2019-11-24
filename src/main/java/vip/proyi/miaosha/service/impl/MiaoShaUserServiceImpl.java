package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.mapper.IMiaoShaUserMapper;
import vip.proyi.miaosha.service.IMiaoShaUserService;

@Service
public class MiaoShaUserServiceImpl extends ServiceImpl<IMiaoShaUserMapper, MiaoShaUser> implements IMiaoShaUserService {
}
