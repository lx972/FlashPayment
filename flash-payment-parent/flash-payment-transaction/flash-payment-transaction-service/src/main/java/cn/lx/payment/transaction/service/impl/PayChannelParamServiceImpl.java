package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.cache.Cache;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.transaction.api.IPayChannelParamService;
import cn.lx.payment.transaction.covert.PayChannelParamCovert;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import cn.lx.payment.transaction.entity.AppPlatformChannel;
import cn.lx.payment.transaction.entity.PayChannelParam;
import cn.lx.payment.transaction.mapper.AppPlatformChannelMapper;
import cn.lx.payment.transaction.mapper.PayChannelParamMapper;
import cn.lx.payment.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@Service
@Transactional
public class PayChannelParamServiceImpl implements IPayChannelParamService {

    @Autowired
    private PayChannelParamMapper payChannelParamMapper;

    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;

    @Resource
    private Cache cache;

    /**
     * 更新和添加登录商户的支付参数
     *
     * @param merchantId         商户id
     * @param payChannelParamDTO 支付参数
     */
    @Override
    public void savePayChannelParam(Long merchantId, PayChannelParamDTO payChannelParamDTO) throws BusinessException {
        if (Strings.isNullOrEmpty(payChannelParamDTO.getAppId()) ||
                Strings.isNullOrEmpty(payChannelParamDTO.getPlatformChannelCode()) ||
                Strings.isNullOrEmpty(payChannelParamDTO.getPayChannel())) {
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //根据应用与平台服务绑定的id和平台服务编码找到应用与平台服务绑定的id
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>()
                .eq(AppPlatformChannel::getAppId, payChannelParamDTO.getAppId())
                .eq(AppPlatformChannel::getPlatformChannel, payChannelParamDTO.getPlatformChannelCode()));
        if (appPlatformChannel == null) {
            //未绑定
            throw new BusinessException(CommonErrorCode.E_300010);
        }
        //根据应用与平台服务绑定的id和原始支付渠道编码查询支付参数信息
        PayChannelParam channelParam = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>()
                .eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannel.getId())
                .eq(PayChannelParam::getPayChannel, payChannelParamDTO.getPayChannel()));

        if (channelParam != null) {
            //更新支付参数
            channelParam.setChannelName(payChannelParamDTO.getChannelName());
            channelParam.setParam(payChannelParamDTO.getParam());
            payChannelParamMapper.updateById(channelParam);
        } else {
            //新增支付参数
            //转化
            PayChannelParam payChannelParam = PayChannelParamCovert.INSTANCE.dto2entity(payChannelParamDTO);
            //设置应用与平台服务绑定的id
            payChannelParam.setAppPlatformChannelId(appPlatformChannel.getId());
            //设置商户id
            payChannelParam.setMerchantId(merchantId);
            //设置主键为空
            payChannelParam.setId(null);
            payChannelParamMapper.insert(payChannelParam);
        }

        //更新缓存
        updateCache(payChannelParamDTO.getAppId(), payChannelParamDTO.getPlatformChannelCode());

    }

    /**
     * 更新缓存
     *
     * @param appId           应用id
     * @param platformChannel 平台服务类型编码
     */
    private void updateCache(String appId, String platformChannel) {
        //构建key 如：SJ_PAY_PARAM:b910da455bc84514b324656e1088320b:shanju_c2b
        String keyBuilder = RedisUtil.keyBuilder(appId
                , platformChannel);
        //判断缓存是否存在
        if (cache.exists(keyBuilder)) {
            //存在缓存，删除
            cache.del(keyBuilder);
        }
        //从数据库中查询应用和平台服务下的参数列表
        List<PayChannelParam> payChannelParams = payChannelParamMapper.queryPayChannelParams(appId
                , platformChannel);
        if (payChannelParams != null) {
            //加入到缓存中
            List<PayChannelParamDTO> payChannelParamDTOList = PayChannelParamCovert.INSTANCE.entityList2dtoList(payChannelParams);
            cache.set(keyBuilder, JSON.toJSONString(payChannelParamDTOList));
        }
    }

    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     *
     * @param appId           应用id
     * @param platformChannel 平台服务类型编码
     * @return
     */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParams(String appId, String platformChannel) {
        //构建key 如：SJ_PAY_PARAM:b910da455bc84514b324656e1088320b:shanju_c2b
        String keyBuilder = RedisUtil.keyBuilder(appId
                , platformChannel);
        //判断缓存是否存在
        if (cache.exists(keyBuilder)) {
            //存在缓存，获取
            String value = cache.get(keyBuilder);
            return JSON.parseArray(value, PayChannelParamDTO.class);
        }
        //不存在缓存
        List<PayChannelParam> payChannelParamList = payChannelParamMapper.queryPayChannelParams(appId, platformChannel);
        List<PayChannelParamDTO> payChannelParamDTOList = PayChannelParamCovert.INSTANCE.entityList2dtoList(payChannelParamList);
        if (payChannelParamDTOList != null) {
            //加入到缓存中
            cache.set(keyBuilder, JSON.toJSONString(payChannelParamDTOList));
        }
        return payChannelParamDTOList;
    }

    /**
     * 获取指定应用指定服务类型下指定原始支付方式所包含的原始支付渠道参数
     *
     * @param appId           应用id
     * @param platformChannel 平台服务类型编码
     * @param payChannel      原始支付渠道编码
     * @return
     */
    @Override
    public PayChannelParamDTO queryPayChannelParam(String appId, String platformChannel, String payChannel) {
        PayChannelParam payChannelParam = payChannelParamMapper.queryPayChannelParam(appId, platformChannel, payChannel);
        PayChannelParamDTO payChannelParamDTO = PayChannelParamCovert.INSTANCE.entity2dto(payChannelParam);
        return payChannelParamDTO;
    }

    /**
     * 根据订单号查询出支付者的阿里支付参数
     *
     * @param out_trade_no 支付时传入的商户订单号
     * @param trade_no     支付时返回的支付宝交易号
     * @return
     */
    @Override
    public AliConfigParam queryPayChannelParamByNo(String out_trade_no, String trade_no) throws BusinessException {

        //根据条件查询支付参数信息
        PayChannelParam payChannelParam = payChannelParamMapper.queryPayChannelParamByNo(out_trade_no, trade_no);
        if (payChannelParam == null) {
            throw new BusinessException(CommonErrorCode.E_300014);
        }
        PayChannelParamDTO payChannelParamDTO = PayChannelParamCovert.INSTANCE.entity2dto(payChannelParam);
        //支付宝渠道参数
        AliConfigParam aliConfigParam = JSON.parseObject(payChannelParamDTO.getParam(), AliConfigParam.class);
        aliConfigParam.setCharest("utf-8");
        return aliConfigParam;
    }
}
