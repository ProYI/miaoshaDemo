package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.mapper.IMiaoShaOrderMapper;
import vip.proyi.miaosha.service.IMiaoShaOrderService;

@Service
public class MiaoShaOrderServiceImpl extends ServiceImpl<IMiaoShaOrderMapper, MiaoShaOrder> implements IMiaoShaOrderService {
}
