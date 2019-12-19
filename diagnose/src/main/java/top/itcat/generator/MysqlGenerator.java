package top.itcat.generator;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

public class MysqlGenerator {
    private static String[] tables = {"result_item"};
    private static File file = new File("generator");
    private static String path = file.getAbsolutePath();
    private static String user = "root";
    private static String pwd = "root";
    private static String url = "jdbc:mysql://47.100.217.150:3306/diagnose?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";

    public static void main(String[] args) throws Exception {
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                new GlobalConfig()
                        .setOutputDir(path + "/src/main/java")
                        .setFileOverride(true)
                        .setActiveRecord(true)
                        .setEnableCache(true)
                        .setBaseResultMap(true)
                        .setBaseColumnList(true)
                        .setOpen(false)
                        .setAuthor("ITcathyh")
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setServiceName("%sService")
                        .setServiceImplName("%sServiceImpl")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(String fieldType) {
                                return super.processTypeConvert(fieldType);
                            }
                        })
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUsername(user)
                        .setPassword(pwd)
                        .setUrl(url)
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(tables) // 需要生成的表
                        .setSuperServiceImplClass("top.itcat.aop.multipledb.BaseServiceImpl")
                        .setTableFillList(tableFillList)
                        .setEntityBooleanColumnRemoveIsPrefix(true)
                        .setEntityLombokModel(true)
//                        .setSuperEntityClass("top.itcat.entity.BaseUser")
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setParent("top.itcat")// 自定义包路径
                        .setEntity("entity")
                        .setMapper("mapper")
                        .setService("service")
                        .setServiceImpl("service.impl")
        ).setCfg(
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return path + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
                    }
                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
        );

        mpg.execute();
    }

}