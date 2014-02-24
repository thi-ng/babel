# thing-babel

A Leiningen project template for literate Clojure projects w/ Emacs &
[org-mode](http://orgmode.org).

This template does *not* create a working Lein project folder, but
generates a number of ORG files (most importantly
[index.org](https://raw.github.com/thi-ng/thing-babel/master/src/leiningen/new/thing_babel/index.org))
which contains a parametric description of the Lein project to be
generated (and regenerated in the future). These ORG files must be
tangled with the generated `tangle.sh` script and will generate a
working lein project from those.

The template assumes a x-platform CLJX project structure and
configures a Lein project with the currently latest versions of:

* Clojure 1.6.0-beta1
* ClojureScript 0.0-2173
* cljx 0.3.2
* cljsbuild 1.0.2
* austin 0.1.4
* criterium 0.4.1

## Usage

'''bash
lein new thing-babel org.foo/bar \
  author "my name" \
  email "a@b.com" \
  url "http://project.com" \
  author-url "http://home.com" \
  license "asl" \
  target "generated" \
  desc "Another great project"

Generating fresh literate programming project: org.foo/bar
generated project dir   : bar
artefact group ID       : org.foo
project author          : my name
author email            : a@b.com
license                 : Apache Software License 2.0
description             : Another great project
path for gen sources    : generated/
project root namespace  : org.foo.bar
'''

Apart from the project name all other options are optional (indeed).
If omitted, some values will assume the following defaults:

* **author** - local user name (via `(System/getProperty "user.name")`)
* **license** - "epl" (lein's default choice)*
* **desc** - "FIXME: ..." (project description)
* **target** - "babel" (source for generated/tangled source blocks
  from .org files)
