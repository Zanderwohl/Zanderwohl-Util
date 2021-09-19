# Zanderwohl Util

This is a simple set of utilities that I seem to be using and reusing in my projects.
It includes a handful of things I really wish were in Java. One of them in particular is trivially
easy and computationally cheap to perform in MUMPS, a language from 1966 for the PDP-7; but Java isn't
really built with all these modern data structures like trees in mind.

## Phrase Index

An index designed to allow for easy and vague searching of a list.

Because of the luxury of MUMPS that my employer so generously provided me, I got really used to simple
routines being able to match phrases by the most spurious of connections.

For example, say you want to search for the phrase for "Barium Fluoroscopic Enema" but that takes far
too long to type. Instead, type "Bar flu en" and the Phrase Index will know exactly what you want!

The programmer must simply feed the full phrases into the Phrase Index, and the index will... well...
*index* the phrases for easy partial searching.

## File Loader

Basically a wrapper class that handles all the messy parts of file loading.

## Precedence

Say you have a setting, like MAX_USERS that can be configured a few different ways: there's a config file,
where the user can set it, but also a command-line argument that the user can override the setting with.
And if neither of those has a value, you want a default. These functions make that easy.

Simply feed them into the appropriate function, and the last item in the list with a valid value will be
chosen.

Example:
```Java
// Sets the message of the day
string motd = Precedence.lastNonNull("Message of the day not set.",
        database.query("motd"), args[1]);
```

*In the example above, the message of the day is set to the value stored in a database, unless a message
was fed into the command line when this program started. If the database errors or returns nothing AND
nothing was fed into the command line, the message of the day is set to "Message of the day not set."*

## Properties

Takes in a string of line-delimited properties in the form of "key=value" and turns it into a hashmap.

For example, if the file below is loaded and processed with Properties:

```
max_users=20
motd=My Server
error=Flagrant System
virus=very yes
```

then all these values will be available in a HashMap:

```Java
HashMap<String, String> properties = Properties.toHashMap(fileContents);
properties.get("max_users"); //returns the string "20"
properties.get("error"); //returns the string "Flagrant System"
```