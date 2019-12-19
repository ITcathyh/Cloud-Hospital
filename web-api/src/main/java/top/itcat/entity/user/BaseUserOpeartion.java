package top.itcat.entity.user;

public interface BaseUserOpeartion {
    public Long getId();

    public void setId(Long id);

    public String getCode();

    public void setCode(String code);

    public String getPassword();

    public void setPassword(String password);

    public String getRealname();

    public void setRealname(String realname);

    public Long getDepartmentId();

    public void setDepartmentId(Long departmentId);
}
