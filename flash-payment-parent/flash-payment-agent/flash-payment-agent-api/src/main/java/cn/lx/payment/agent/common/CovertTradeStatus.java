package cn.lx.payment.agent.common;

import cn.lx.payment.domain.TradeStatus;

/**
 * cn.lx.payment.agent.common
 *
 * @Author Administrator
 * @date 11:49
 */
public class CovertTradeStatus {

    /**
     * 解析支付宝的订单状态为闪聚平台的状态
     *
     * @param aliTradeStatus
     * @return
     */
    public static TradeStatus covertAliTradeStatusToShanjuCode(String aliTradeStatus) {
        switch (aliTradeStatus) {
            case AliCodeConstants.TRADE_FINISHED:
            case AliCodeConstants.TRADE_SUCCESS:
                return TradeStatus.SUCCESS;//业务交易支付 明确成功
            case AliCodeConstants.TRADE_CLOSED:
                return TradeStatus.REVOKED;//交易已撤销
            case AliCodeConstants.WAIT_BUYER_PAY:
                return TradeStatus.USERPAYING;//交易新建，等待支付
            default:
                return TradeStatus.FAILED;//交易失败
        }
    }
}
