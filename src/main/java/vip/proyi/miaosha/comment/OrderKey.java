package vip.proyi.miaosha.comment;

public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getMiaoShaOrderByUidGid = new OrderKey("moug");
}
