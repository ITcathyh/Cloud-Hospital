package top.itcat.controller.util;

import com.google.protobuf.Descriptors;
import top.itcat.pb_gen.api.depart.manage.DepartManage;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ControllerUtil {
    public static Map<String, Object> getFieldMap(com.google.protobuf.GeneratedMessageV3.Builder msg) {
        Map<String, Object> map = new HashMap<>();

        for (Descriptors.FieldDescriptor fieldDescriptor : msg.getDescriptorForType().getFields()) {
            if (msg.getAllFields().containsKey(fieldDescriptor)) {
                map.put(fieldDescriptor.getName(), msg.getAllFields().get(fieldDescriptor));
            }
        }

        return map;
    }


    public static void get(com.google.protobuf.GeneratedMessageV3.Builder<DepartManage.QueryDepartmentRequest.Builder> msg,
                           Object rpcbean) {
        try {
            Class<?> clazz = msg.getClass();
            Class<?> rpcclazz = rpcbean.getClass();
            System.out.println(Arrays.toString(rpcclazz.getFields()));

            for (Descriptors.FieldDescriptor fieldDescriptor : msg.getDescriptorForType().getFields()) {
                if (msg.getAllFields().containsKey(fieldDescriptor)) {
                    StringBuilder sb = new StringBuilder(fieldDescriptor.getName());
                    sb.replace(0, 1, String.valueOf(Character.toUpperCase(fieldDescriptor.getName().charAt(0))));
                    Method getMethod = clazz.getMethod("get" + sb.toString());
                    Method setMethod = rpcclazz.getMethod("set" + sb.toString());
                    System.out.println(getMethod.invoke(msg));
                    setMethod.invoke(rpcbean, getMethod.invoke(msg));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
