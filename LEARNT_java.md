LEARNT
----

1. An FileStream doesn't exist,
    `FileInputStream`, `FileOutputStream` do
    And the FileInputStream are initialised with a `File` object
2. For handling 'multiple' exception, and we do the same thing with both (eg. print and return).
    In such a case, simply separate both exception types with a '|'.
    And no different variables, it will just be same
3. * If an object inside the `println()` has the `toString()` method, then it will _implicitly_ be called on it.
    For eg.

    `System.out.println(digest.digest());`

    Warning by IDE -> _Implicit call to 'toString()' on array returned by call to 'digest.digest()'_
    * BUT, Calling `toString()` on it, also shows this warning-
    _Call to 'toString()' on array_
    * And, it's better to *use the* `java.util.Arrays.toString()`, instead of a direct call to toString() method

4. `HashMap` is a `Collection` in Java

5. The `default` access modifier, when used inside class, makes the member behave as if 'package-private'

6. `private` members can be modified, through an object, if in a method of that class itself, let it be static. But we cant do so from an object, declared inside method of other class

7. Top-level classes can NOT be `private`

8. Java generics don't work with primitive types.

9. If you assign null, in constructor or in IIB, or with assignment (IIB), and the member is final... THEN EVEN IN THIS CASE, we cant change the value, hence it will remain `null` forever !?

10. enum variables don't behave like references... tHEY are likely passed by value, but Println prints their name, not a numeric value... ? Eg.
  `Direction d = Direction.NORTH;
  System.out.println(d);
  tryToChangeEnum(d);
  System.out.println(d);`
  Prints ->
    `NORTH
    NORTH`

11. Getting random unsigned integers -> new Random().nextInt() & Integer.MAX_VALUE;
    This works, since it 'zero out' the sign bit

12. Initialisation of ArrayList ->
    * Explicitly calling `add()`, AFTER instantiation
    * Shorthand for calling `add()`, DURING instantiation
    * Passing `Arrays.asList(...)` to the constructor
    * Passing `List.of(...)` to the constructor
    * Passing a `Collection` to the constructor

13. When creating 2D arrays in Java, the first dimension(ie. rows) is must! (While in C++, giving 'columns' is necessary! )

***ERRORS and Exceptions***
----

* Error: class MainClass is public, should be declared in a file named MainClass.java
  * _NOTE_ - The filename here was 'exception.java'. In this case, if we compile first, using javac, then we get above error. But, if we directly reun using 'java exception.java' then it runs fine! :D
* Error:(37, 41) java: unreported exception java.io.IOException; must be caught or declared to be thrown
* Error:(28, 30) java: Alternatives in a multi-catch statement cannot be related by subclassing
  Alternative java.io.FileNotFoundException is a subclass of alternative java.io.IOException
* Error:(25, 47) java: unreported exception java.security.NoSuchAlgorithmException; must be caught or declared to be thrown
* Warning - The serializable class  does not declare a static final serialVersionUID field of type longJava(536871008)
  `Stops = new ArrayList<Point>(){ ..... };`
* Error - Can only iterate over an array or an instance of java.lang.Iterable    Java(536871493)
