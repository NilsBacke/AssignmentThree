
// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // sort the strings in the list lexicographically
  ILoString sort();

  // inserts a given string s into the front of the list of strings
  ILoString insertString(String s);

  // returns the first String in the list
  String getFirst();

  // returns the rest of the list of Strings, everything but the first
  ILoString getRest();

  // returns true if the list is sorted lexicographically
  boolean isSorted();

  // a helper method for isSorted
  boolean isSortedHelp(String acc);

  // returns a new list of strings with contents interleaved from this list of
  // strings and the other list of strings
  ILoString interleave(ILoString other);

  // returns a new list of strings with merged contents from this list of strings
  // and the other list of strings
  ILoString merge(ILoString other);

  // returns a new list of strings with reversed contents
  ILoString reverse();

  // a helper method for reverse
  ILoString reverseHelp(ILoString acc);

  // returns true if the list contains pairs of identical strings
  boolean isDoubledList();

  // returns true if this list contains the same words reading the list in either
  // order.
  boolean isPalindromeList();

  // a helper method for isPalindromeList
  boolean isPalindromeListHelp(ILoString acc);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  /*
   * TEMPLATE FIELDS:
   * 
   * METHODS ... this.combine() ... -- String ... this.sort() ... -- ILoString ...
   * this.insertString() ... -- ILoString ... this.getFirst() ... -- String ...
   * this.getRest() ... -- String ... this.isSorted() ... -- boolean ...
   * this.isSortedHelp() ... -- boolean ... this.interleave() ... -- ILoString ...
   * this.merge() ... -- ILoString ... this.reverse() ... -- ILoString ...
   * this.reverseHelp() ... -- ILoString ... this.isDoubledList() ... -- boolean
   * ... this.isPalindromeList() ... -- boolean ... this.isPalindromeListHelp()
   * ... -- boolean
   * 
   * METHODS FOR FIELDS
   */

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // empty list cannot be sorted
  @Override
  public ILoString sort() {
    return this;
  }

  // creates a new list with a length of one with the given String
  @Override
  public ILoString insertString(String s) {
    return new ConsLoString(s, this);
  }

  // empty list does not have a first
  @Override
  public String getFirst() {
    return "";
  }

  // empty list does not have a rest
  @Override
  public ILoString getRest() {
    return this;
  }

  // empty list is always sorted
  @Override
  public boolean isSorted() {
    return true;
  }

  // empty list is always sorted
  @Override
  public boolean isSortedHelp(String acc) {
    return true;
  }

  // empty list interleaved with other list is just other list
  @Override
  public ILoString interleave(ILoString other) {
    return other;
  }

  // empty list merged with other list is just other list
  @Override
  public ILoString merge(ILoString other) {
    return other;
  }

  // empty list is reversed
  @Override
  public ILoString reverse() {
    return this;
  }

  // return the accumulator of the helper method
  @Override
  public ILoString reverseHelp(ILoString acc) {
    return acc;
  }

  // empty list is always doubled
  @Override
  public boolean isDoubledList() {
    return true;
  }

  // empty list is always a palindrome
  @Override
  public boolean isPalindromeList() {
    return true;
  }

  // empty list is always a palindrome
  @Override
  public boolean isPalindromeListHelp(ILoString acc) {
    return true;
  }

}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  // constructs a new ConsLoString
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE FIELDS: ... this.first ... -- String ... this.rest ... -- ILoString
   * 
   * METHODS ... this.combine() ... -- String ... this.sort() ... -- ILoString ...
   * this.insertString() ... -- ILoString ... this.getFirst() ... -- String ...
   * this.getRest() ... -- String ... this.isSorted() ... -- boolean ...
   * this.isSortedHelp() ... -- boolean ... this.interleave() ... -- ILoString ...
   * this.merge() ... -- ILoString ... this.reverse() ... -- ILoString ...
   * this.reverseHelp() ... -- ILoString ... this.isDoubledList() ... -- boolean
   * ... this.isPalindromeList() ... -- boolean ... this.isPalindromeListHelp()
   * ... -- boolean
   * 
   * METHODS FOR FIELDS ... this.first.concat(String) ... -- String ...
   * this.first.compareTo(String) ... -- int ... this.rest.combine() ... -- String
   * ... this.rest.combine() ... -- String ... this.rest.sort() ... -- ILoString
   * ... this.rest.insertString() ... -- ILoString ... this.rest.getFirst() ... --
   * String ... this.rest.getRest() ... -- String ... this.rest.isSorted() ... --
   * boolean ... this.rest.isSortedHelp() ... -- boolean ...
   * this.rest.interleave() ... -- ILoString ... this.rest.merge() ... --
   * ILoString ... this.rest.reverse() ... -- ILoString ...
   * this.rest.reverseHelp() ... -- ILoString ... this.rest.isDoubledList() ... --
   * boolean ... this.rest.isPalindromeList() ... -- boolean ...
   * this.rest.isPalindromeListHelp() ... -- boolean
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sorts the given list, tries to insert each string into it's appropriate spot
  @Override
  public ILoString sort() {
    return this.rest.sort().insertString(this.first);
  }

  // determines where to insert the given string in the list
  @Override
  public ILoString insertString(String s) {
    if (s.compareToIgnoreCase(this.first) <= 0) {
      return new ConsLoString(s, this);
    } else {
      return new ConsLoString(this.first, this.rest.insertString(s));
    }
  }

  // returns the first string in the list
  @Override
  public String getFirst() {
    return this.first;
  }

  // returns the rest of the list
  @Override
  public ILoString getRest() {
    return this.rest;
  }

  // returns true if the list is sorted lexicographically
  @Override
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first);
  }

  // determines whether the first String comes before the given string,
  // lexicographically
  @Override
  public boolean isSortedHelp(String acc) {
    if (this.first.compareToIgnoreCase(acc) >= 0) {
      return this.rest.isSortedHelp(this.first);
    } else {
      return false;
    }
  }

  // interleaves the two given strings
  @Override
  public ILoString interleave(ILoString other) {
    return new ConsLoString(this.first, other.interleave(this.rest));
  }

  // merges the two given strings, then sorts
  @Override
  public ILoString merge(ILoString other) {
    return this.interleave(other).sort();
  }

  // reverses the given list
  // calls the helper method
  @Override
  public ILoString reverse() {
    return this.reverseHelp(new MtLoString());
  }

  // helper method for reverse
  // acc represents the current reversed list, initialize with an MtLoString
  @Override
  public ILoString reverseHelp(ILoString acc) {
    return this.rest.reverseHelp(new ConsLoString(this.first, acc));
  }

  // returns true if the current list contains pairs of identical strings
  // compares the first two strings, then recurses on the rest rest of the list
  @Override
  public boolean isDoubledList() {
    return this.first.equals(this.getRest().getFirst()) && this.getRest().getRest().isDoubledList();
  }

  // returns true if the given list is a palindrome of strings
  @Override
  public boolean isPalindromeList() {
    return this.isPalindromeListHelp(this.reverse());
  }

  // palindrome helper method
  // first determines if the first string in the list equals the first string of
  // the reversed list
  @Override
  public boolean isPalindromeListHelp(ILoString acc) {
    return this.first.equals(acc.getFirst()) && this.rest.isPalindromeListHelp(acc.getRest());
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));
  ILoString marySorted = new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("lamb.",
      new ConsLoString("little ", new ConsLoString("Mary ", new MtLoString())))));

  ILoString interleave1 = new ConsLoString("Mary",
      new ConsLoString("a", new ConsLoString("lamb.", new MtLoString())));
  ILoString interleave2 = new ConsLoString("had",
      new ConsLoString("little", new ConsLoString("!", new MtLoString())));
  ILoString interleaveResult = new ConsLoString("Mary",
      new ConsLoString("had", new ConsLoString("a", new ConsLoString("little",
          new ConsLoString("lamb.", new ConsLoString("!", new MtLoString()))))));

  ILoString allA = new ConsLoString("a",
      new ConsLoString("a", new ConsLoString("a", new MtLoString())));

  ILoString sorted1 = new ConsLoString("duckling", new ConsLoString("mary", new MtLoString()));
  ILoString sorted2 = new ConsLoString("apple", new ConsLoString("banana", new MtLoString()));
  ILoString mergeResult = new ConsLoString("apple", new ConsLoString("banana",
      new ConsLoString("duckling", new ConsLoString("mary", new MtLoString()))));
  ILoString interleaveResult2 = new ConsLoString("duckling", new ConsLoString("apple",
      new ConsLoString("mary", new ConsLoString("banana", new MtLoString()))));

  ILoString reverseInterleave1 = new ConsLoString("lamb.",
      new ConsLoString("a", new ConsLoString("Mary", new MtLoString())));

  ILoString doubled = new ConsLoString("apple", new ConsLoString("apple",
      new ConsLoString("banana", new ConsLoString("banana", new MtLoString()))));

  ILoString palindrome = new ConsLoString("Mary",
      new ConsLoString("a", new ConsLoString("Mary", new MtLoString())));
  ILoString palindrome2 = new ConsLoString("apple",
      new ConsLoString("banana", new ConsLoString("cheese",
          new ConsLoString("banana", new ConsLoString("apple", new MtLoString())))));
  ILoString palindrome3 = new ConsLoString("apple", new ConsLoString("banana",
      new ConsLoString("banana", new ConsLoString("apple", new MtLoString()))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method sort for the lists of Strings
  boolean testSort(Tester t) {
    return t.checkExpect(mary.sort(), marySorted) && t.checkExpect(sorted2.sort(), sorted2);
  }

  // test the method isSorted for the lists of Strings
  boolean testIsSorted1(Tester t) {
    return t.checkExpect(marySorted.isSorted(), true) && t.checkExpect(mary.isSorted(), false)
        && t.checkExpect(allA.isSorted(), true);
  }

  // test the method interleave for the lists of Strings
  boolean testInterleave(Tester t) {
    return t.checkExpect(interleave1.interleave(interleave2), interleaveResult)
        && t.checkExpect(sorted1.interleave(sorted2), interleaveResult2);
  }

  // test the method merge for the lists of Strings
  boolean testMerge(Tester t) {
    return t.checkExpect(sorted1.merge(sorted2), mergeResult);
  }

  // test the method reverse for the lists of Strings
  boolean testReverse(Tester t) {
    return t.checkExpect(interleave1.reverse(), reverseInterleave1)
        && t.checkExpect(palindrome.reverse(), palindrome);
  }

  // test the method isDoubledList for the lists of Strings
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(doubled.isDoubledList(), true)
        && t.checkExpect(interleave1.isDoubledList(), false);
  }

  // test the method isPalindromeList for the lists of Strings
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(palindrome.isPalindromeList(), true)
        && t.checkExpect(palindrome2.isPalindromeList(), true)
        && t.checkExpect(palindrome3.isPalindromeList(), true)
        && t.checkExpect(reverseInterleave1.isPalindromeList(), false);
  }
}