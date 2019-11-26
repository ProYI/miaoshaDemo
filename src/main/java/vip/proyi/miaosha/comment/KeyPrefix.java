package vip.proyi.miaosha.comment;

/**
 * 接口 ->> 抽象类 ->> 实现类 模板模式
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
