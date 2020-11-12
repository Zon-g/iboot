package org.project.common.utils;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Helper class to make it available to export records into an excel document.
 *
 * @author Zon-g
 */
@Component
public class Excels {

    /**
     * Accepts an object implemented <code>interface Row</code>, a writable object (called
     * the record) and fields alias of that object, and flushes that record into the specified
     * row by <code>Reflecting</code> and invoking the <code>Getter</code> methods.
     *
     * @param row         place to be written.
     * @param obj         object to be written.
     * @param fieldsAlias fields alias of that object.
     */
    public void flush(Row row, Object obj, String[][] fieldsAlias) {
        for (int i = 0; i < fieldsAlias.length; i++) {
            String field = fieldsAlias[i][0];
            try {
                Method method = obj.getClass().getMethod("get" + Character
                        .toUpperCase(field.charAt(0)) + field.substring(1));
                Object o = method.invoke(obj);
                String s;
                if (o instanceof Date) {
                    s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(((Date) o));
                } else if (o == null) {
                    s = "";
                } else {
                    s = o.toString();
                }
                row.createCell(i).setCellValue(s);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
