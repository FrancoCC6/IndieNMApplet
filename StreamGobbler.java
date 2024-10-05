// Cred: https://www.baeldung.com/run-shell-command-in-java
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.function.Consumer;

public class StreamGobbler implements Runnable {
    private InputStream input_stream;
    private Consumer<String> consumer;

    public StreamGobbler(InputStream input_stream, Consumer<String> consumer) {
        this.input_stream = input_stream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(input_stream))
        .lines()
        .forEach(consumer);
    }
}
