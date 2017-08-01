publishMavenStyle := true

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

pomExtra in Global := <url>https://github.com/giabao/php-utils</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:giabao/php-utils.git</url>
    <connection>scm:git:git@github.com:giabao/php-utils.git</connection>
  </scm>
  <developers>
    <developer>
      <id>giabao</id>
      <name>Gia Bảo</name>
      <email>giabao@sandinh.net</email>
      <organization>Sân Đình</organization>
      <organizationUrl>http://sandinh.com</organizationUrl>
    </developer>
    <developer>
      <id>cuongdd2</id>
      <name>Đỗ Đức Cường</name>
      <email>cuongdd@sandinh.net</email>
      <organization>Sân Đình</organization>
      <organizationUrl>http://sandinh.com</organizationUrl>
    </developer>
    <developer>
      <id>kayahr</id>
      <name>Klaus Reimer</name>
      <email>k@ailis.de</email>
      <url>http://www.ailis.de/~k/</url>
    </developer>
  </developers>
