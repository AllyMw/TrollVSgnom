package mitrofanov.configuration;

import lombok.Getter;
import mitrofanov.resolvers.CommandResolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Configuration {

    public static Map<String, CommandResolver> resolvers = new HashMap<>();
    private static final String packageName = System.getenv("resolvers-package");

    static {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        InputStream resourceAsStream = systemClassLoader.getResourceAsStream(packageName.replaceAll("[.]", "/")); //
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(resourceAsStream));
        Stream<String> lines = bufferedInputStream.lines();
        List<? extends Class<?>> allClasses = lines.filter(line -> line.endsWith(".class"))
                .map(line -> {
                    try {
                        return Class.forName(
                                packageName + "." + line.substring(0, line.lastIndexOf('.')));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        List<? extends Class<?>> filteredClasses = allClasses.stream()
                .filter(elem -> Arrays.stream(elem.getInterfaces()).parallel().filter(iface -> Objects.equals(iface, CommandResolver.class)).count() > 0)
                .collect(Collectors.toList());

        filteredClasses.forEach(elem -> {

            try {
                CommandResolver
                        resolver = (CommandResolver) elem.getConstructor().newInstance();
                resolvers.put(resolver.getCommandName(), resolver);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
