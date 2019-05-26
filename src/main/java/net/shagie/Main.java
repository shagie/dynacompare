package net.shagie;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SomeObj first = new SomeObj("a", 9, 4.1);
        SomeObj second = new SomeObj("b", 4, 4.1);

        List<ObjFields> fields = Arrays.asList(ObjFields.FOO, ObjFields.BAZ);
        System.out.println(SomeObj.compare(fields, first, second));
        System.out.println(SomeObj.compare(fields, second, first));

        System.out.println();

        fields = Arrays.asList(ObjFields.FOO, ObjFields.BAR);
        System.out.println(SomeObj.compare(fields, first, second));
        fields = Arrays.asList(ObjFields.BAR, ObjFields.FOO);
        System.out.println(SomeObj.compare(fields, first, second));

        System.out.println();

        System.out.println(SomeObj.compare(fields, first, first));

    }
}
