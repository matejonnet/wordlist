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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.matejonnet.wordlist.Main.getCharset;

public class MainTest {

    @Test
    public void testGenerate() throws Exception {
        Main main = new Main();
        String mixalpha = getCharset("mixalpha");
        String numericSymbols14 = getCharset("numeric-symbols14");
        List<String> wordList = main.generate(Arrays.asList(new String[]{mixalpha, numericSymbols14}), new ArrayList<>());
        System.out.println("Generated #" + wordList.size() + " words.");
        Assert.assertEquals(mixalpha.length() * numericSymbols14.length(), wordList.size());
        //wordList.stream().forEach(word -> System.out.println(word));
        Assert.assertEquals("a0", wordList.get(0));
        Assert.assertEquals("Z=", wordList.get(wordList.size() -1 ));
        Assert.assertTrue(wordList.contains("b!"));
    }

    @Test
    public void testGenerateWithExclusion() throws Exception {
        Main main = new Main();
        String mixalpha = getCharset("mixalpha");
        String numericSymbols14 = getCharset("numeric-symbols14");
        List<String> excludeWords = Arrays.asList(new String[]{"a0", "b0", "Z="});
        List<String> wordList = main.generate(Arrays.asList(new String[]{mixalpha, numericSymbols14}), excludeWords);
        System.out.println("Generated #" + wordList.size() + " words.");
        Assert.assertEquals(mixalpha.length() * numericSymbols14.length() - excludeWords.size(), wordList.size());
        Assert.assertFalse(wordList.contains("a0"));
        Assert.assertFalse(wordList.contains("b0"));
        Assert.assertFalse(wordList.contains("Z="));
        Assert.assertTrue(wordList.contains("b!"));
        Assert.assertTrue(wordList.contains("Z0"));
    }
}