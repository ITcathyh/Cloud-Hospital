package top.itcat.entity;

public enum HosiptitalLevel {
    Three_S(0), Three_A(1), Three_B(2), Three_C(3), Two_A(4), Two_B(5), Two_C(6), One_A(7), One_B(8), One_C(9);
    public int level;

    HosiptitalLevel(int i) {
        this.level = i;
    }
}
