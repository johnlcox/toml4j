# toml4j

[![Build Status](https://travis-ci.org/johnlcox/toml4j.png)](https://travis-ci.org/johnlcox/toml4j)

A Java parser for [toml](https://github.com/mojombo/toml) files, obviously based heavily on the jackson databind API.  The parse result is a navigable tree of nodes.

## Usage

```toml
# config.toml
name = "awesome service"

[server]
ip = "192.168.0.1"
port = 12000
```

```java
InputStream tomlInpuStream = new FileInputStream("config.toml");
TomlParser tomlParser = new TomlParser();
TomlNode tomlNode = tomlParser.parse(tomlInputStream);

String serviceName = tomlNode.get("name").stringValue();

TomlNode serverNode = tomlNode.get("server");
String serverIp = serverNode.get("ip").stringValue();
long serverPort = serverNode.get("port").longValue();
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
