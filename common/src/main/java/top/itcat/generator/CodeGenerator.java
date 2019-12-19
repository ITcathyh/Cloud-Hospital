package top.itcat.generator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

public class CodeGenerator {
    public static void generateAllConverMethod(String... excludeNames) {
        File[] files = new File("src/main/java/top/itcat/entity").listFiles();

        try {
            for (File file : Objects.requireNonNull(files)) {
                boolean filter = false;

                for (String name : excludeNames) {
                    if (file.getName().startsWith(name)) {
                        filter = true;
                        break;
                    }
                }

                if (filter) {
                    continue;
                }

                generateConverMethod(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateConverMethod(String... fileName) {
        File[] files = new File("src/main/java/top/itcat/entity").listFiles();

        try {
            for (File file : Objects.requireNonNull(files)) {
                boolean shouldDo = false;

                for (String name : fileName) {
                    if (file.getName().startsWith(name)) {
                        shouldDo = true;
                        break;
                    }
                }

                if (!shouldDo) {
                    continue;
                }

                generateConverMethod(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateConverMethod(File file) throws Exception {
        String convertTem = "    public static %s convert(top.itcat.rpc.service.model.%s rpcbean) {\n" +
                "        if (rpcbean == null) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        %s bean = new %s();\n" +
                "%s\n" +
                "        return bean;\n" +
                "    }\n";

        String format = "        if (rpcbean.isSet%s()) {\n" +
                "            bean.set%s(rpcbean.get%s());\n" +
                "        }\n";
        String convertConverRPCBeanTem = "    public static top.itcat.rpc.service.model.%s convertRPCBean(%s bean) {\n" +
                "        if (bean == null) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        top.itcat.rpc.service.model.%s rpcbean = new top.itcat.rpc.service.model.%s();\n" +
                "%s\n" +
                "        return rpcbean;\n" +
                "    }\n";

        String converRPCBeanformat = "        rpcbean.set%s(bean.get%s());\n";
        Class c = Class.forName("top.itcat.entity." + file.getName().replaceAll(".java", ""));
        Field[] fields = c.getDeclaredFields();
        StringBuilder methodPudding = new StringBuilder();
        StringBuilder converRPCBeanMethodPudding = new StringBuilder();


        for (Field f : fields) {
            String name = f.getName();

            if (name.equals("valid") || name.equals("serialVersionUID")) {
                continue;
            }

            StringBuilder sb = new StringBuilder(name);
            sb.replace(0, 1, String.valueOf(Character.toUpperCase(name.charAt(0))));
            methodPudding.append(String.format(format, sb.toString(), sb.toString(), sb.toString()));
            converRPCBeanMethodPudding.append(String.format(converRPCBeanformat, sb.toString(), sb.toString()));
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        StringBuilder fileContent = new StringBuilder();
        String str;

        while ((str = in.readLine()) != null) {
            if ("}".equals(str)) {
                continue;
            }

            fileContent.append(str).append("\n");
        }

        fileContent.append(String.format(convertTem, c.getSimpleName(), c.getSimpleName(),
                c.getSimpleName(), c.getSimpleName(), methodPudding.toString())).append("\n");
        fileContent.append(String.format(convertConverRPCBeanTem, c.getSimpleName(), c.getSimpleName(),
                c.getSimpleName(), c.getSimpleName(), converRPCBeanMethodPudding.toString())).append("\n");
        fileContent.append("}");

        in.close();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

        out.write(fileContent.toString());
        out.flush();
        out.close();

        String serviceAddFormt = "    @Override\n" +
                "    public AddOrUpdate%sResponse addOrUpdate%s(AddOrUpdate%sRequest req) throws TException {\n" +
                "        AddOrUpdate%sResponse rsp = new AddOrUpdate%sResponse();\n" +
                "        List<%s> list =\n" +
                "                req.getBeanList().parallelStream().map(%s::convert).collect(Collectors.toList());\n" +
                "        %sService.insertOrUpdateBatch(list);\n" +
                "        \n" +
                "        rsp.setBaseResp(ThriftUtil.getBaseResp());\n" +
                "        return rsp;\n" +
                "    }";
        StringBuilder sb = new StringBuilder(c.getSimpleName());
        sb.replace(0, 1, String.valueOf(Character.toLowerCase(c.getSimpleName().charAt(0))));
        String service = String.format(serviceAddFormt, c.getSimpleName(), c.getSimpleName(), c.getSimpleName(),
                c.getSimpleName(), c.getSimpleName(), c.getSimpleName(), c.getSimpleName(), sb.toString());
        System.out.println(service);
    }
}
