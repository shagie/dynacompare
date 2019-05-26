package net.shagie;

import net.shagie.compares.DoubleCompare;
import net.shagie.compares.IntCompare;
import net.shagie.compares.StringCompare;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SomeObj {
    @Compare(field = ObjFields.FOO, compare = StringCompare.class)
    String foo;

    @Compare(field = ObjFields.BAR, compare = IntCompare.class)
    Integer bar;

    @Compare(field = ObjFields.BAZ, compare = DoubleCompare.class)
    Double baz;

    private static Map<ObjFields, FieldData> FIELD_MAP = null;

    public SomeObj(String foo, Integer bar, Double baz) {
        this.foo = foo;
        this.bar = bar;
        this.baz = baz;
    }

    private static Map<ObjFields, FieldData> getFields() throws InstantiationException, IllegalAccessException {
        if (FIELD_MAP != null) {
            return FIELD_MAP;
        }
        Map<ObjFields, FieldData> rv = new EnumMap<>(ObjFields.class);
        for (Field f : SomeObj.class.getDeclaredFields()) {
            if (f.isAnnotationPresent(Compare.class)) {
                Compare a = f.getAnnotation(Compare.class);
                rv.put(a.field(), new FieldData(a, f));
            }
        }

        FIELD_MAP = rv;
        return rv;
    }

    public static int compare(List<ObjFields> fields, SomeObj o1, SomeObj o2) throws IllegalAccessException, InstantiationException {
        Map<ObjFields, FieldData> fieldMap = getFields();
        return fields.stream()
                .map(fieldMap::get)
                .map(fd -> {
                    try {
                        return fd.comparator.compare(fd.field.get(o1), fd.field.get(o2));
                    } catch (IllegalAccessException e) {
                        return 0;
                    }
                })
                .filter(c -> c != 0)
                .findFirst()
                .orElse(0)
        ;
    }

    private static class FieldData {
        Compare anno;
        Field field;
        Comparator comparator;

        public FieldData(Compare anno, Field field) throws IllegalAccessException, InstantiationException {
            this.anno = anno;
            this.field = field;
            comparator = anno.compare().newInstance();
        }
    }
}
