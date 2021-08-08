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



# `insert`

Example:

```sh
dbtool insert "INSERT INTO test.TBL_EMPLOYEES (first_name, last_name, email) VALUES ('Lokesh', 'Gupta', 'abc@gmail.com');"
```

# `delete`

Example:

```sh
dbtool delete "DELETE FROM test.TBL_EMPLOYEES WHERE last_name='Gupta';"
```

# `update`

Example:

```sh
dbtool update "UPDATE test.TBL_EMPLOYEES SET first_name = 'NewName' WHERE last_name='Gupta';"
```

# Roadmap

# `extract`

Command will extract content of listed tables to sql script file. Each line will be new sql insert command.

# `search`

This is special version of `select` command. Where standard `select` command looks for specific columns in specific table this command will look into all tables in schema and colums in where statement will be matched using regexp.

It's important to remember that this is not valid sql statement it's just way of expression. This statement will be broken up
and translated in to many separate sql select commands.

Example, to list all rows (from all tables) where column contains `name` in its name and value 'Gupta'

```sql
select name as [name] from test.* where name='Gupta'
```


# `reformat`
## `reformat insert`
## `reformat select`
## `reformat dates`


