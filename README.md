# thing-babel

A Leiningen project template for literate Clojure projects w/ Emacs &
[org-mode](http://orgmode.org).

This template does *not* create a working Lein project directory per
se, but constitutes more of a meta template consisting of a several
`.org` files (most importantly
[index.org](https://raw.github.com/thi-ng/babel/master/src/leiningen/new/thing_babel/index.org))
which contains both a nice template for a library README and a
parametric description of the Lein project to be generated and
regenerated in the future. These `.org` files must be tangled with the
supplied `tangle.sh` script and will generate a working lein project
from those.

## New in 0.2.0

Generated `.org` files are now placed in dedicated `src` & `test`
sub folders to provide a cleaner project structure.

A `setup.org` file is created to define common & shared org-mode
configuration for all project files.

New in version 0.2.0 is also the presence of
[libraryofbabel.org](https://raw.github.com/thi-ng/babel/master/src/leiningen/new/thing_babel/libraryofbabel.org),
org-mode's mechanism to support re-usable & parametric code templates.
The generated `.org` file for the main namespace demonstrates the use
of such code templates (albeit in a very construed way). A much better
and more realistic use for these templates is to provide skeleton
implementations when working with Clojure protocols.

## Objective

The template is aimed at an x-platform CLJX project structure and
configures a Lein project with the currently latest versions of:

* Clojure 1.6.0-RC1
* ClojureScript 0.0-2156 (for compatibility w/ Austin)
* cljx 0.3.2
* cljsbuild 1.0.2
* clojurescript.test 0.2.2
* austin 0.1.4
* criterium 0.4.1

## Usage

The project name given can be fully qualified (as in the example
below), however the created directory will only take the last name part
minus the leading group ID (if any). The fully qualified name is used
to define namespaces & target paths within the generated .org files
and will also be inserted into the resulting `project.clj` file.

```bash
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
project url             : http://project.com
project author          : my name
author url              : http://home.com
author email            : a@b.com
license                 : Apache Software License 2.0
description             : Another great project
path for gen sources    : generated/
project root namespace  : org.foo.bar

# switch into newly created project folder
cd bar 

# (re)generate actual lein project & sources
./tangle.sh *.org test/*.org

# switch into generated project (value of `target` key above)
cd generated

# trigger cleaning, cljx processing & testing
lein cleantest
Rewriting src/cljx to target/classes (clj) with features #{clj} and 0 transformations.
Rewriting src/cljx to target/classes (cljs) with features #{cljs} and 1 transformations.
Rewriting test/cljx to target/test-classes (clj) with features #{clj} and 0 transformations.
Rewriting test/cljx to target/test-classes (cljs) with features #{cljs} and 1 transformations.

lein test org.foo.bar.test.core

lein test :only org.foo.bar.test.core/epic-fail

FAIL in (epic-fail) (core.clj:12)
FIXME
expected: (= 3 (+ 1 1))
  actual: (not (= 3 2))

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
Tests failed.
```

Success!

Apart from the project name all other options are optional (indeed).
If omitted, some values will assume the following defaults:

* **author** - local user name (via `(System/getProperty
  "user.name")`)
* **desc** - `"FIXME: ..."` (project description)
* **license** - `"epl"` (lein's default choice, currently only other
  choice is `asl`)
* **target** - `"babel"` (source folder prefix for generated/tangled
  source blocks from .org files)
* **url** - `"http://github.com"` (project url)

## Misc

I recommend using the Emacs
[Leuven theme](https://github.com/fniessen/emacs-leuven-theme) for a
great org-mode experience...
