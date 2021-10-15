package testrunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class TestRunner {
    public static void main(String[] args)
            throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        executeMain(args,
                rounds.Y2020.A.Plates.Solution.class);
    }

    private static void executeMain(
            String[] args,
            Class<?> testClass)
            throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final URL inputFile = testClass.getResource("input.txt");
        assert inputFile != null;
        System.setIn(inputFile.openStream());

        final PrintStream out = new PrintStream(new FileOutputStream("src/testrunner/output.txt"));
        System.setOut(out);

        testClass.getMethod("main", String[].class).invoke(testClass, new Object[] {args});

        out.flush();
        out.close();
    }
}
