# DBTool

Command line tool to do some "ad-hoc" stuff with data in your database.

List of supported commands (functionalities) can be seen below.

Requirements:
- Java 11 (JRE)
- Command line execution environment (Bash, Powershel,...)


# Implemented commands

# `select`

Executes simple literal SQL command in selected database.

Example:

Long version:

```sh
dbtool select -p=sa -u=sa -j="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from 'src/test/resources/sql/init.sql'" "select * from test.TBL_EMPLOYEES"
```

With default values for parameters:

```sh
dbtool select "select * from test.TBL_EMPLOYEES"
```


# Roadmap

# `insert`
# `delete`
# `update`
# `extract`
# `search`

# `reformat`
## `reformat insert`
## `reformat select`
## `reformat dates`


