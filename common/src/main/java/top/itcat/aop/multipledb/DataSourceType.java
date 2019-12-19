package top.itcat.aop.multipledb;

public enum DataSourceType {
    MASTER(1), SLAVE(2);
    public int val;

    DataSourceType(int val) {
        this.val = val;
    }
}
