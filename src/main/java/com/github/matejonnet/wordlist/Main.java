/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.matejonnet.wordlist;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class Main {

    private static Properties defaultCharsets;

    static {
        defaultCharsets = new Properties();
        try {
            defaultCharsets.load(ClassLoader.getSystemResourceAsStream("charset.lst"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int excluded = 0;

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        final List<String> excludeWords = new ArrayList<>();
        List<String> pattern = new ArrayList<>();
        Optional<String> excludeWordsFilePath = Optional.empty();
        for (int i = 0; i < args.length;) {
            String arg = args[i];
            if (arg.equals("-e")) {
                i++;
                excludeWordsFilePath = Optional.of(args[i]);
            } else {
                pattern.add(getCharset(arg));
            }
            i++;
        }

        excludeWordsFilePath.ifPresent(path -> {
            try {
                excludeWords.addAll(Files.readAllLines(Paths.get(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        main.generate(pattern, excludeWords, System.out);
    }

    public void generate(List<String> pattern, List<String> excludeWords, PrintStream printStream) {
        List<CharsetIterator> iterators = new ArrayList<>();
        long aboutToGenerate = 1L;
        for (String charset : pattern) {
            iterators.add(new CharsetIterator(charset));
            aboutToGenerate = aboutToGenerate * charset.length();
        }

        System.out.println("Generating #" + aboutToGenerate + " words.");

        while (true) {
            String word = "";
            for (int i = 0; i < iterators.size(); i++) {
                CharsetIterator iterator = iterators.get(i);
                String ch = iterator.get();
                word += ch;
            }
            if (!excludeWords.contains(word)) {
                printStream.println(word);
            } else {
                excluded ++;
            }

            if (moveIterators(iterators) == false) {
                break;
            }
        }

        System.out.println("Excluded " + excluded + " words.");
    }

    public List<String> generate(List<String> pattern, List<String> excludeWords) throws IOException {
        List<String> words = new ArrayList<>();

        PipedInputStream sink = new PipedInputStream();
        PrintStream ps = new PrintStream(new PipedOutputStream(sink)) {
            @Override
            public void println(String s) {
                words.add(s);
            }
        };

        generate(pattern, excludeWords, ps);

        return words;
    }

    private boolean moveIterators(List<CharsetIterator> iterators) {
        int interatorIndex = iterators.size() - 1;
        while (true) {
            CharsetIterator iterator = iterators.get(interatorIndex);
            if (iterator.next() == null) {
                interatorIndex --;
                if (interatorIndex < 0) {
                    return false;
                }
            } else {
                break;
            }
        }
        return true;
    }

    public static String getCharset(String key) {
        String charset = defaultCharsets.getProperty(key);
        return charset.substring(1, charset.length() - 1);
    }

}
