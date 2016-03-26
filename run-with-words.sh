#!/bin/bash
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#
#
# Example usage:
#
# Words from the wordlist are added to the end of the command
#./run-with-words.sh "echo aaa bbb" wordlist.txt
#
# xx is replaced with words from the wordlist
#./run-with-words.sh "echo aaa xx bbb" wordlist.txt
#
# if the program like cryptsetup is prompting for a "word" you can try
#./run-with-words.sh "echo xx | cryptsetup luksOpen /dev/xvdc backup2" wordlist.txt

IFS=$'\n'       # make newlines the only separator
set -f          # disable globbing
count=0
for i in $(cat $2); do
  ((count++))
  #  ($1 "$i")
  #escape
  word=$(printf '%q\n' $i)

  if [[ "$1" =~ "xx" ]]; then
    cmd=${1//xx/"$word"}
  else
    cmd="$1 $word"
  fi
  echo "Trying #$count cmd: $cmd"
  eval $cmd
  ret_code=$?
  if [ $ret_code == 0 ]; then
    echo ":)"
    break
  fi
done
