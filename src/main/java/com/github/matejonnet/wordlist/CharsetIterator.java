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

import java.util.Arrays;
import java.util.List;

/**
* @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
*/
class CharsetIterator {
    private List<String> charset;
    private int index = -1;
    String current = null;

    public CharsetIterator(String charsetString) {
        this.charset = Arrays.asList(charsetString.split(""));
        current = charset.get(++index);
    }

    public String next() {
        if (index == charset.size() - 1) {
            index = -1;
            current = charset.get(++index);
            return null;
        }
        current = charset.get(++index);
        return current;
    }

    public String get() {
        return current;
    }
}
