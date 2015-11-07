package com.volley.swastik.utils;

import com.path.android.jobqueue.Job;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class JobKeyUtil {

    public static String keyForJob(Job job) {
        return job.getClass().getSimpleName() + " " + keyStringForInstance(job, false, true, true);
    }

    private static List<Field> fieldsForClass(Class klass, boolean includePublic, boolean includePrivate, boolean includeProtected) {
        Field[] fields = klass.getDeclaredFields();

        ArrayList fieldsFiltered = new ArrayList();

        for (Field field : fields) {
            if (!field.isSynthetic() && (
                    includePublic && Modifier.isPublic(field.getModifiers()) ||
                            includePrivate && Modifier.isPrivate(field.getModifiers()) ||
                            includeProtected && Modifier.isProtected(field.getModifiers()))) {
                fieldsFiltered.add(field);
            }
        }

        return fieldsFiltered;
    }

    private static String keyStringForInstance(Object object, boolean includePublic, boolean includePrivate, boolean includeProtected) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Class klass = object.getClass();
        List<Field> fields = fieldsForClass(klass, includePublic, includePrivate, includeProtected);
        int numberOfFields = fields.size();
        int currentField = 0;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldObject = field.get(object);
                stringBuilder.append("\"");
                stringBuilder.append(field.getName());
                stringBuilder.append("\"");
                stringBuilder.append(" : ");
                if (field.getType().isPrimitive()) {
                    stringBuilder.append(fieldObject.toString());
                } else {
                    if (field.getType() == String.class) {
                        String fieldString = field.get(object).toString();
                        stringBuilder.append("\"");
                        stringBuilder.append(fieldString);
                        stringBuilder.append("\"");
                    } else {
                        String fieldString = keyStringForInstance(fieldObject, false, true, true);
                        stringBuilder.append(fieldString);
                    }
                }
                if (++currentField != numberOfFields) {
                    stringBuilder.append(", ");
                }
            } catch (IllegalAccessException e) {
                stringBuilder.append("?");
            }
        }
        stringBuilder.append("}");

        String returnValue = stringBuilder.toString();
        return returnValue;
    }
}
