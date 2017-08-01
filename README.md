php-utils
=========
[![Build Status](https://travis-ci.org/giabao/php-utils.svg)](https://travis-ci.org/giabao/php-utils)

Utils for serialize/un-serialize Scala object to PHP serialized string

This project is ported from Java project [Pherialize](https://github.com/kayahr/pherialize). Credit to [kayahr](https://github.com/kayahr).

### Sample usage
```scala
	PhpObject.stringify(Map("key" -> "value", "key2" -> "value2")
	PhpObject.parse("a:2:{i:0;i:123;i:1;s:3:\"abc\";}")
```
### Output
```console
	a:2:{s:3:"key";s:5:"value";s:4:"key2";s:6:"value2";}
	Map(0 -> 123, 1 -> "abc")
```
### See test cases for more information :D

### Changelogs
see [CHANGES.md](CHANGES.md)

### Licence
This software is licensed under the Apache 2 license:
http://www.apache.org/licenses/LICENSE-2.0

Copyright 2014-2017 Sân Đình (https://sandinh.com)
