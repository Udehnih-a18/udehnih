package id.ac.ui.cs.advprog.udehnihh.payment.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class ObjectToMapMapper {

    public static <T> HashMap<String, String> mapToObject(T object) {
        HashMap<String, String> map = new HashMap<>();
        if (object != null) {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    map.put(field.getName().toString(), field.get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
