# toml4j

[![Build Status](https://travis-ci.org/johnlcox/toml4j.png?branch=master)](https://travis-ci.org/johnlcox/toml4j)

A Java parser for [toml](https://github.com/mojombo/toml) files, based heavily on the jackson databind node API.  The parse result is a navigable tree of nodes.

## Usage

Use the `TomlParser` to parse a TOML file or string.  The parsed result can be consumed in two ways.

### Key/Value Based

If the TOML structure is already known, the key/value approach is a simple abstraction of the TOML tree with easy key based accessors.

```java
InputStream tomlInpuStream = new FileInputStream("config.toml");
Toml toml = Toml.from(new FileInputStream("config.toml"));

String serviceName = toml.getString("name");

String dbServer = toml.getString("database.server");
boolean isDbEnabled = toml.getBoolean("database.enabled");

String serverIp = toml.getString("server.alpha.ip");
```

### Tree Based

With the tree based approach it is possible to easily traverse a TOML structure that is not known a priori.

```java
InputStream tomlInpuStream = new FileInputStream("config.toml");
TomlNode tomlNode = new TomlParser().parse(tomlInputStream);

String serviceName = tomlNode.get("name").stringValue();

TomlNode databaseNode = tomlNode.get("database");
String dbServer = serverNode.get("server").stringValue();
boolean isDbEnabled = serverNode.get("enabled").booleanValue();

TomlNode serverNode = tomlNode.get("servers").get("alpha");
String serverIp = serverNode.get("ip").stringValue();
```

## License

Copyright 2013 John Leacox

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
